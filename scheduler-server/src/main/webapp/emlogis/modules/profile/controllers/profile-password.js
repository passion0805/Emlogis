angular.module('emlogis.profile').controller('ProfilePasswordCtrl', ['$scope', 'authService', 'applicationContext', 'dataService',
    function($scope, authService, applicationContext, dataService) {

      $scope.tabs[2].active = true;

      $scope.passwords = {
        currentPassword: "",
        newPassword: "",
        repeatPassword: ""
      };

      $scope.passwordsMatch = function() {
        return $scope.passwords.newPassword && $scope.passwords.newPassword === $scope.passwords.repeatPassword;
      };

      $scope.changePassword = function() {
        var hashedPassword = CryptoJS.SHA256($scope.passwords.currentPassword + "." + authService.getSessionInfo().tenantId).toString();

        dataService.changePassword(hashedPassword, $scope.passwords.newPassword).
          then(function() {
            applicationContext.setNotificationMsgWithValues("Password changed", 'success', true);
          }, function(err) {
            applicationContext.setNotificationMsgWithValues(err.data.message, 'danger', true);
          });
      };

    }
  ]
);
