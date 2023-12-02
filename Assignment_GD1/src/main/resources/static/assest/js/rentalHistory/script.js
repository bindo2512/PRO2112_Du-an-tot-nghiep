app.controller("user-management-ctrl", function($scope, $http){
    $scope.rentals = [];
    $scope.retaildetail = [];
    $scope.userForm = {};
    $scope.username = $("#username").text()
    $scope.formEnabled = false;
    
    $scope.init = function() {
        console.log(username)
        $http.get("/rest/rental/username/" + $scope.username).then(resp =>{
            console.log(resp.data)
            $scope.rentals = resp.data;
        }).catch(error => {
            console.log(error)
        })
        $http.get("/rest/account/" + $scope.username).then(resp => {
            console.log(resp.data)
            $scope.userForm = resp.data;
        }).catch(error =>  {
            console.log(error)
        })
    },

    $scope.toggleForm = function() {
        $scope.formEnabled = !$scope.formEnabled;

    };
    $scope.$watch('formEnabled', function(afterValue, beforeValue) {
        if (beforeValue === true && afterValue === false) {
            var item = angular.copy($scope.userForm);
            $http.put("/rest/account/${item.username}", item).then(resp => {
                alert("Update thành công")
            }).catch(error => {
                alert("Lỗi cập nhật");
                console.log(error);
            })
        }
    });
    $scope.imageChange = function(files) {
        var data = new FormData();
        data.append('file', files[0]);
        $http.post('/rest/upload/image/users', data, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(resp => {
            $scope.userForm.accountdetail.image = resp.data.name;
        }).catch(error => {
            alert("Lỗi update file hình ảnh");
            console.log(error);
        })
    }

    $scope.watch = function(item) {
        $http.get("/rest/rental/id/" + item.retailid).then(resp => {
            $scope.retaildetail = resp.data;
        })
    },
    
    $scope.init();
})