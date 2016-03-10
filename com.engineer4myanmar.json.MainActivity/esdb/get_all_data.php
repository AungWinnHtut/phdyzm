<?php
/*
 * Following code will list all the logins
 */
// array for JSON response
$response = array();
// include db connect class
require_once __DIR__ . '/db_user_connect.php';

// connecting to db
$con = new DB_CONNECT();
$con->connect();

// get all logins from logins table
$result = mysqli_query($con->myconn, "SELECT * FROM `user`") or die(mysql_error());

// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // users node
    $response["logins"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $login = array();
        $login["user_id"] = $row["user_id"];
        $login["user_name"] = $row["user_name"];
        $login["user_password"] = $row["user_password"];     

        // push single login into final response array
        array_push($response["logins"], $login);
    }
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
?>
