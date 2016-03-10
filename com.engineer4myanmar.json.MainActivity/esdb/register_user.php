<?php
/*
 * Following code will register new user
 */
// array for JSON response
$response = array();
// check for required fields
if (isset($_POST['user_name']) && isset($_POST['user_password']) ) {
   
   
	$user_name = $_POST['user_name'];
	$user_email = $_POST['user_email'];
    $user_password = $_POST['user_password'];  
	$user_phoneno = $_POST['user_phoneno'];
	$user_dob = $_POST['user_dob'];
	$user_profession = $_POST['user_profession'];
	$user_city = $_POST['user_city'];
// include db connect class
require_once __DIR__ . '/db_user_connect.php';

// connecting to db
$con = new DB_CONNECT();
$con->connect();

// create new user by inserting 
$result = mysqli_query($con->myconn,"INSERT INTO user(user_id, user_name,user_email,user_password,user_phoneno,user_dob,user_profession,user_city)
	VALUES(null, '$user_name','$user_email','$user_password','$user_phoneno','$user_dob','$user_profession','$user_city')");

// check for empty result
if ($result){
    // success
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no logins found
    $response["success"] = 0;
    $response["message"] = "No logins found";
    // echo no users JSON
    echo json_encode($response);
}
}
else{ // no logins found
    $response["success"] = 0;
    $response["message"] = "Required fields are missing";
    // echo no users JSON
    echo json_encode($response);
	}
?>
