<?php
	$name = $_GET['name'];
	//$name = "Aster Hotel";
	$response = array();
	//echo $name;
	// include db connect class
	require_once __DIR__ . '/db_user_connect.php';

	// connecting to db
	$con = new DB_CONNECT();
	$con->connect();
	//$sql ="SELECT category.catid, category.name as cat_name, information.id, information.name as info_name, information.* FROM category INNER JOIN  information ON category.catid=information.cat_id WHERE information.name='$name'";
	$sql ="SELECT category.catid, category.name as cat_name, information.id, information.name as info_name FROM category INNER JOIN  information ON category.catid=information.cat_id WHERE information.name='$name'";
	//echo $sql;
	$res = mysqli_query($con->myconn,$sql);
	$row = mysqli_num_rows($res);
	$row = mysqli_fetch_assoc($res);
	$table_name = $row['cat_name'];
	//echo $table_name;
	
	$sql ="SELECT $table_name.* , information.* FROM $table_name INNER JOIN  information ON $table_name.info_id=information.id WHERE information.name='$name'";
	//echo $sql;
	$res = mysqli_query($con->myconn,$sql);
	$row = mysqli_num_rows($res);
		if($row> 0){
			//echo "OL";
			$response["detail"] = array();
			while ($row = mysqli_fetch_assoc($res)){
				//hotel (cat name_
				$detail = array();
				$detail["price_high"] =  $row['price_high'];
				$detail["price_low"] = $row['price_low'];	
				//$detail["cuisine"] = $row['cuisine'];
				//infomation
				$detail["name"] = $row['name'];
				$detail["address"] = $row['address'];
				$detail["phone_no"] = $row['phone_no'];
				$detail["url"] = $row['url'];
				$detail["rating"] = $row['rating'];
				$detail["lat"] = $row['lat'];
				$detail["lng"] = $row['lng'];
				//echo "<br>";
				//echo "<b>".$name. "</b><br>". $address. "<br>". $phone_no. "<br>".$url. "<br>".$rating."<br>".$lat."<br>".$lng;	
				//echo "<b>".$price_high. "</b><br>". $price_low. "<br>". $cuisine. "<br>";	
				array_push($response["detail"], $detail);
				
			}
		}
		else{
			// no logins found
    		$response["success"] = 0;
    		$response["message"] = "No detail found";
    		// echo no users JSON
    		echo json_encode($response);
		}

		// check for empty result
		if ($res){
    		// success
    		$response["success"] = 1;
    		// echoing JSON response
    		echo json_encode($response);
		} else {
    		// no logins found
    		$response["success"] = 0;
    		$response["message"] = "No detail found";
    		// echo no users JSON
    		echo json_encode($response);
		}


?>