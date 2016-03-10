<?php
	//$response = array();
	
	// include db connect class
	require_once __DIR__ . '/db_user_connect.php';

	// connecting to db
	$con = new DB_CONNECT();
	$con->connect();
	/////////////////////////////////////
	//$sql = "SELECT * FROM category";
	//$res = mysqli_query($con->myconn, $sql );
	//while ($row = mysqli_fetch_assoc($res)){
	//	$cat_id = $row['catid'];
	//	$name = $row['name'];		
	//}
	///////////////////////////////////
	if (isset($_GET['catalog'])){
	//if(1){
		$catalog = $_GET['catalog'];
		$rating =$_GET['rating'];
		$p1 = $_GET['p1'];
		$p2 = $_GET['p2'];	
		$cui = $_GET['cuisine'];


		$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\ninputs cat_name=".$catalog." rating=".$rating." p1=".$p1." cursine=".$cui." \n");
    	//echo "success appending file";
    	fclose($myfile);

		//$catalog = "hotel";
		//$rating ="2";
		//$p1 = "10";
		//$p2 = "1000";	
		//$cui = "chinese";

		//FROM cat INNER JOIN book ON cat.ID = book.cat_id GROUP BY cat.name;
		
		//$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%' OR  BETWEEN $catalog.price_high=$p2 AND $catalog.price_low=$p1  GROUP BY information.name "  ;
		$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%'  AND  $p1 BETWEEN $catalog.price_high AND $catalog.price_low AND   $p2 BETWEEN $catalog.price_high AND $catalog.price_low   GROUP BY information.name "  ;
		//$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%' AND  $p1 = $catalog.price_low AND   $p2 = $catalog.price_high  GROUP BY information.name "  ;
		$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\n sql".$sql." \n");
    	fclose($myfile);
		//echo $sql;
		$res = mysqli_query($con->myconn,$sql) or die(mysqli_error());


		

		$row = mysqli_num_rows($res);

		$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\n Roll  ".$row." \n");
    	fclose($myfile);

		if($row> 0){
			

				$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    			fwrite($myfile, "\n data exist \n");
    			//echo "success appending file";
    			fclose($myfile);

			$response["result"]=array();
			while ($row = mysqli_fetch_assoc($res)){
				$found = array();
				$found['info_name'] = $row['info_name'];	
				$found['address'] = $row['address'];
				$found['phone_no'] = $row['phone_no'];
				array_push($response["result"],$found);


				$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    			fwrite($myfile, "\ninputs cat_name=".$row['info_name']." rating=".$row['address']." p1=".$row['phone_no']." \n");
    			//echo "success appending file";
    			fclose($myfile);

			}
			$response["success"]=1;
			echo json_encode($response);
		}
		else{
			$response["success"]=0;
			$response["message"]="Not found";
			echo json_encode($response);
		}
		
	}
	// check for empty result
		if ($res){
    		// success
    		$response["success"] = 1;
    		// echoing JSON response
    		echo json_encode($response);
		} else {
			$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    		fwrite($myfile, "\nno inputs \n");
    		//echo "success appending file";
    		fclose($myfile);
    		// no logins found
    		$response["success"] = 0;
    		$response["message"] = "No detail found";
    		// echo no users JSON
    		echo json_encode($response);
		}
?>
