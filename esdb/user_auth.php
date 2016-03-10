<?php

/*
 * Following code will get single product details
 * A product is identified by product id (user_name)
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_user_connect.php';

// connecting to db
$con = new DB_CONNECT();
$con->connect();
// check for post data
if (isset($_GET["user_name"])) {
    $user_name = $_GET['user_name'];

    // get a product from products table
    $result = mysqli_query($con->myconn, "SELECT user_password FROM login WHERE user_name = $user_name");

    if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {

            $result = mysqli_fetch_array($result);

            $product = array();
            $product["user_password"] = $result["user_password"];            
            // success
            $response["success"] = 1;

            // user node
            $response["product"] = array();

            array_push($response["product"], $product);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>