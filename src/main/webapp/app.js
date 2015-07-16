angular.module('app', ['cui', 'ngRoute'])

.constant('applicationInfo', {
  name: 'Sitraka Migrator',
  version: '1.2.3'
})

.config(function($routeProvider) {
  $routeProvider
  .when('/', {
    templateUrl: 'home.html',
    controller: 'HomeCtrl'
  }).when('/migrations/:migration', {
    templateUrl: 'migrations.html',
    controller: 'MigrationsCtrl'
  });
})

.controller('AppCtrl', function($scope, auth, $location) {
  $scope.auth = auth;
  $scope.$on('$locationChangeStart', function() {
    if (!auth.isAuthenticated()) {
      if ($location.path() !== '/login') {
        $location.path('/login');
      }
    } else {
      if ($location.path() === '/login') {
        $location.path('/');
      }
    }
  });
})

.controller('StartScreenCtrl', function($scope, applicationInfo, auth) {
  $scope.startScreenSettings = {
    applicationName: applicationInfo.name
  }
  $scope.onSignIn = function(userData) {
    auth.login(userData.username, userData.password);
  }
})

.controller('AppFrameCtrl', function($scope, applicationInfo, applicationInfo, auth, $http, cuiAlertService, cuiLoading) {
  $scope.applicationInfo = applicationInfo;
  $scope.user = auth.getUsername();
  $scope.navItems = [{
    label: 'Home',
    icon: 'home',
    href: '#/'
  }, {
    label: 'Migrations',
    icon: 'rocket',
    children: []
  }];

  cuiLoading($http.get('api/migrations').then(function success(response) {
    var data = response.data;
    angular.forEach(data, function(datum) {
      $scope.navItems[1].children.push({
        label: datum.name,
        href: '#/migrations/' + datum.migrationId
      });
    });
  }, function failure() {
    cuiAlertService.danger('The list of migrations could not be loaded.')
  }));

})

.controller('AboutBoxCtrl', function($scope, cuiAboutBox, applicationInfo) {
  var about = cuiAboutBox({
    applicationName: applicationInfo.name,
    version: applicationInfo.version
  });
  $scope.showAboutBox = about.modal.show;
})

.controller('HomeCtrl', function() {})


.controller('MigrationsCtrl', function($scope, $routeParams, $http, cuiModal, cuiLoading, cuiAlertService, $route) {
  $scope.migration = $routeParams.migration;
  cuiLoading($http.get('api/migrations/' + $scope.migration).then(function success(response) {
    $scope.migrationData = response.data;
  }, function failure(err) {
    cuiAlertService.danger('Migration "' + $scope.migration + '" could not be loaded.');
  }));
  cuiLoading($http.get('api/migrations/' + $scope.migration + '/mailboxes').then(function success(response) {
    $scope.mailboxData = response.data;
  }, function failure(err) {
    cuiAlertService.danger('Mailboxes for Migration "' + $scope.migration + '" could not be loaded.');
  }));

  var modalScope = cuiModal({
    templateUrl: 'addEmail.html'
  });
  modalScope.account = {};
  modalScope.addAccount = function(account) {
    modalScope.modal.hide().then(function() {
      var obj = {
        migrationId: $scope.migration,
        email: account.email,
        emails: account.emails || 0,
        calendars: account.calendars || 0,
        contacts: account.contacts || 0,
        memos: account.memos || 0
      }
      // $scope.migrationData.mailboxes.push(angular.copy(account));
      cuiLoading($http({
        method: 'POST',
        url: 'api/mailboxes',
        data: obj,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        // Puts the data into properly encoded format
        transformRequest: function(obj) {
          var str = [];
          for(var p in obj)
          str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
          return str.join("&");
        }
      }).then(function success(response) {
        $route.reload();
      }, function failure(err) {
        cuiAlertService.danger('Error adding Mailbox "' + obj.email + '" to migration.');
      }))

    });
  }
  $scope.addEmail = function() {
    modalScope.$broadcast('cui:wizard:goto', 0);
    modalScope.account = {};
    modalScope.modal.show();
  }
})

.directive('checkOrX', function() {
  return {
    restrict: 'A',
    scope: {
      'checkOrX': '='
    },
    link: function(scope, element, attrs) {
      scope.$watch('checkOrX', function updateCheckOrX(value) {
        if (value) {
          element.html('✔');
          element.css('color', '#7ab800');
        } else {
          element.html('✗');
          element.css('color', '#ce1126');
        }
      });
    }
  }
})



.constant('sessionStorageUsername', 'username')

.factory('auth', function($location, sessionStorageUsername) {
  return {
    login: function(thisUser, thisPassword) {
      sessionStorage.setItem(sessionStorageUsername, thisUser);
      $location.path('/');
    },
    isAuthenticated: function() {
      return !!sessionStorage.getItem(sessionStorageUsername);
    },
    getUsername: function() {
      return sessionStorage.getItem(sessionStorageUsername);
    },
    logout: function() {
      sessionStorage.removeItem(sessionStorageUsername);
      $location.path('/login');
    }
  }
})

.directive('logout', function(auth) {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      element.on('click', function(e) {
        auth.logout();
        e.preventDefault();
      })
    }
  }
});
