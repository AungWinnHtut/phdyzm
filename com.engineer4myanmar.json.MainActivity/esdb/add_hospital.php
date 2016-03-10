<?php
/*
 * Following code will register new user
 */
// include db connect class
require_once __DIR__ . '/db_user_connect.php';

// connecting to db
$con = new DB_CONNECT();
$con->connect();
// array for JSON response
$response = array();
// check for required fields
if (isset($_POST['catalog']))
{
		$cat_idd = $_POST['catalog'];	

    	$cat_id=(int)$cat_idd+1;
    	$myfile = fopen("add4.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\nfirst input int ".$cat_id."\n");
    	//echo "success appending file";
    	fclose($myfile);

		$sql_cat = "SELECT name FROM category WHERE catid LIKE '$cat_id' ";
		$res_cat = mysqli_query($con->myconn, $sql_cat);
		$row = mysqli_fetch_assoc($res_cat);
		$cat_name =  $row['name'];


    	$myfile = fopen("add4.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\nfsecond sql int ".$cat_name."\n");
    	//echo "success appending file";
    	fclose($myfile);

		$name =$_POST['name'];
		$address =$_POST['address'];
		$phone_no =$_POST['phone_no'];

		$url=$_POST['url'];
		$rating=$_POST['rating'];
		$lat=$_POST['lat'];
		$lng=$_POST['lng'];		
		
		$ph=$_POST['ph'];
		$pl=$_POST['pl'];
		$cu=$_POST['cu'];	

		$myfile = fopen("add4.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\nthird input int ".$address."\n");
    	//echo "success appending file";
    	fclose($myfile);
		
		
		//add value
		$sql_add = "INSERT INTO information (cat_id, name, address, phone_no, url, rating, lat, lng,ext_tb) VALUES ('$cat_id', '$name', '$address', '$phone_no', '$url', '$rating', '$lat', '$lng' ,'$cat_name')";
		$res_add = mysqli_query($con->myconn, $sql_add) ;
		
		$info_id = mysqli_insert_id($con->myconn);
		$myfile = fopen("add4.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\n4th sql out ".$info_id."\n");
    	//echo "success appending file";
    	fclose($myfile);
		//add val to hotel

    	$sql_hotel = "INSERT INTO hospital (info_id, price_high, price_low ) VALUES ('$info_id', '$ph', '$pl' )" ;
		$res_hotel = mysqli_query($con->myconn, $sql_hotel);
		
		


}
else{
	$myfile = fopen("add4.log", "a") or die("Unable to open file!");
    fwrite($myfile, "fail to read input\n");
    //echo "success appending file";
    fclose($myfile);
    // no logins found
    $response["success"] = 0;
    $response["message"] = "No logins found";
    // echo no users JSON
    echo json_encode($response);
}

// check for empty result
if ($res_hotel){
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


