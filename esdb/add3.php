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
if (isset($_POST['catalog']) && isset($_POST['name']) ) {
   
   
	//$cat_id =$_POST['catalog'];

    //$sql_cat = "SELECT name FROM category WHERE catid='$cat_id' ";

    $cat_name = $_POST['catalog'];
    $cat_id=1;

    $myfile = fopen("add.log", "a") or die("Unable to open file!");
    fwrite($myfile, "\ninputs= "+$cat_name);
    //echo "success appending file";
    fclose($myfile);
    ///////////////////////////////////////////
    //$sql_cat ="SELECT catid FROM category WHERE name='$cat_name' "; 
    //$res_cat = mysqli_query($con->myconn, $sql_cat);
    //$row = mysqli_fetch_assoc($res_cat);
    //$cat_id =  $row['catid'];
    //////////////////////////////////////////////////
    if((strcmp($cat_name),"hotel")==0)
    {
        $cat_id=1;
    }
     if((strcmp($cat_name),"bank")==0)
    {
        $cat_id=2;
    }
     if((strcmp($cat_name),"restaurant")==0)
    {
        $cat_id=3;
    }
     if((strcmp($cat_name),"hospital")==0)
    {
        $cat_id=4;
    }
    
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


    $myfile = fopen("add.log", "a") or die("Unable to open file!");
    fwrite($myfile, "inputs= "+$cat_id+$cat_name+$name+$address+$phone_no);
    //echo "success appending file";
    fclose($myfile);

    

// create new user by inserting 

//add value
    $sql_add = "INSERT INTO information (cat_id, name, address, phone_no, url, rating, lat, lng,ext_tb) VALUES ('$cat_id', '$name', '$address', '$phone_no', '$url', '$rating', '$lat', '$lng' ,'$cat_name')";
    $res_add = mysqli_query($con->myconn, $sql_add) ;
        
    $info_id = mysqli_insert_id($con->myconn);
        
    //add val to hotel
    switch ($cat_id) {
        case '1':
                        $sql_hotel = "INSERT INTO hotel (info_id, price_high, price_low, cuisine ) VALUES ('$info_id', '$ph', '$pl', '$cu' )" ;
                        $result = mysqli_query($con->myconn, $sql_hotel);
                        break;
        case '3':
                        $sql_hotel = "INSERT INTO restaurant (info_id, price_high, price_low, cuisine ) VALUES ('$info_id', '$ph', '$pl', '$cu' )" ;
                        $result = mysqli_query($con->myconn, $sql_hotel);
                        break;
        case'2':
                        $sql_hotel = "INSERT INTO bank (info_id) VALUES ('$info_id')";
                        $result = mysqli_query($con->myconn, $sql_hotel);
                        break;
        case'4':
                        $sql_hotel = "INSERT INTO hospital (info_id, price_high, price_low ) VALUES ('$info_id', '$ph', '$pl' )" ;
                        $result = mysqli_query($con->myconn, $sql_hotel);
                        break;
    
        default:
                        $myfile = fopen("add.log", "a") or die("Unable to open file!");
                        fwrite($myfile, "error cat name \n");
                        //echo "success appending file";
                        fclose($myfile);
                        $response["success"] = 0;
                        $response["message"] = "Required fields are missing";
                        // echo no users JSON
                        echo json_encode($response);
        
    }


    
    



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
