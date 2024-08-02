let baseUrl = "http://localhost:8080/api/v1/employee";
const token = localStorage.getItem('token');
const role = localStorage.getItem('role');

if (role === 'USER') {
    $("#btnDeleteEmployee").hide();
}

loadAllEmployees();

$("#btnSaveEmployee").click(function () {
    let empId = $("#txtEmployeeId").val();
    let empName = $("#txtEmployeeName").val();
    let empAddress = $("#txtEmployeeAddress").val();
    let empSalary = $("#txtEmployeeSalary").val();

    const employeeObj = {
        id: empId,
        name: empName,
        address: empAddress,
        salary: empSalary
    };

    $.ajax({
        url: baseUrl + "/saveEmployee",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(employeeObj),
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function (res) {
            loadAllEmployees();
            alert("Employee Saved Successfully...!");
        }, error: function (error) {
            alert("Employee Saved Error...!");
        }
    });
});

$("#btnSearchEmployee").click(function () {
    var searchEmpId = $("#txtEmployeeId").val();
    $.ajax({
        url: baseUrl + "/searchEmployee/" + searchEmpId,
        method: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function (res) {
            $("#txtEmployeeName").val(res.name);
            $("#txtEmployeeAddress").val(res.address);
            $("#txtEmployeeSalary").val(res.salary);
            console.log("Employee Searched Successfully...!");
        },
        error: function (error) {
            console.log("Employee Searched Error...!");
        }
    });
});

$("#btnUpdateEmployee").click(function () {
    let empId = $("#txtEmployeeId").val();
    let empName = $("#txtEmployeeName").val();
    let empAddress = $("#txtEmployeeAddress").val();
    let empSalary = $("#txtEmployeeSalary").val();

    const employeeObj = {
        id: empId,
        name: empName,
        address: empAddress,
        salary: empSalary
    };

    $.ajax({
        url: baseUrl + "/updateEmployee",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(employeeObj),
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function (res) {
            loadAllEmployees();
            alert("Employee Updated Successfully...!");
        },
        error: function (error) {
            alert("Employee Updated Error...!");
        }
    });
});

$("#btnDeleteEmployee").click(function () {
    var deletedEmpId = $("#txtEmployeeId").val();

    $.ajax({
        url: baseUrl + "/deleteEmployee/" + deletedEmpId,
        method: "DELETE",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function (res) {
            loadAllEmployees();
            alert("Employee Deleted Successfully...!");
        },
        error: function (error) {
            alert("Employee Deleted Error...!");
        }
    });
});

$('#btnLoadAllEmployees').click(function () {
    loadAllEmployees();
});

function loadAllEmployees() {
    $('#employeeTable').empty();
    $.ajax({
        url: baseUrl + "/loadAllEmployees",
        method: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function (res) {
            res.forEach(employee => {
                let id = employee.id;
                let name = employee.name;
                let address = employee.address;
                let salary = employee.salary;

                let row = `<tr>
                    <td>${id}</td>
                    <td>${name}</td>
                    <td>${address}</td>
                    <td>${salary}</td>
                </tr>`;
                $("#employeeTable").append(row);
            });
            tableListener();
        },
        error: function (error) {
            console.log("Load All Employees Error...!");
        }
    });
}

function tableListener() {
    $("#employeeTable>tr").on("click", function () {
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let address = $(this).children().eq(2).text();
        let salary = $(this).children().eq(3).text();

        $("#txtEmployeeId").val(id);
        $("#txtEmployeeName").val(name);
        $("#txtEmployeeAddress").val(address);
        $("#txtEmployeeSalary").val(salary);
    });
}