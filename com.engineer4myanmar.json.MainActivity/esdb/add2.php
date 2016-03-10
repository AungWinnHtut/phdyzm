<?php 
$response = array();
include_once("config.php");
if (isset($_POST['catalog']))
{
		$cat_id =$_POST['catalog'];
		$sql_cat = "SELECT name FROM category WHERE catid='$cat_id' ";
		$res_cat = mysqli_query($conn, $sql_cat);
		$row = mysqli_fetch_assoc($res_cat);
		$cat_name =  $row['name'];
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


		
		
		//add value
		$sql_add = "INSERT INTO information (cat_id, name, address, phone_no, url, rating, lat, lng,ext_tb) VALUES ('$cat_id', '$name', '$address', '$phone_no', '$url', '$rating', '$lat', '$lng' ,'$cat_name')";
		$res_add = mysqli_query($conn, $sql_add) ;
		
		$info_id = mysqli_insert_id($conn);
		
		//add val to hotel
		$sql_hotel = "INSERT INTO hotel (info_id, price_high, price_low, cuisine ) VALUES ('$info_id', '$ph', '$pl', '$cu' )" ;
		$res_hotel = mysqli_query($conn, $sql_hotel);
		
		


}
else{
	$myfile = fopen("add2.log", "a") or die("Unable to open file!");
    fwrite($myfile, "fail to read input\n");
    //echo "success appending file";
    fclose($myfile);
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


