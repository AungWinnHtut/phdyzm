<?php
	//$response = array();
	
	include_once("config.php");
	$sql = "SELECT * FROM category";
	$res = mysqli_query($conn, $sql );
	while ($row = mysqli_fetch_assoc($res)){
		$cat_id = $row['catid'];
		$name = $row['name'];		
	}
	
	if (isset($_POST['rating'])){
	
		$catalog = $_POST['catalog'];
		$rating =$_POST['rating'];
		$p1 = $_POST['p1'];
		$p2 = $_POST['p2'];	
		$cui = $_POST['cuisine'];

		$catalog = "hotel";
		$rating ="1";
		$p1 = "10";
		$p2 = "1000";	
		$cui = "chinese";

		//FROM cat INNER JOIN book ON cat.ID = book.cat_id GROUP BY cat.name;
		
		//$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%' OR  BETWEEN $catalog.price_high=$p2 AND $catalog.price_low=$p1  GROUP BY information.name "  ;
		$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%'  AND  $p1 BETWEEN $catalog.price_high AND $catalog.price_low AND   $p2 BETWEEN $catalog.price_high AND $catalog.price_low   GROUP BY information.name "  ;
		//$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%' AND  $p1 = $catalog.price_low AND   $p2 = $catalog.price_high  GROUP BY information.name "  ;
		
		//echo $sql;
		$res = mysqli_query($conn,$sql) or die(mysqli_error());
		$row = mysqli_num_rows($res);
		if($row> 0){
			//echo "OL";
			$response["result"]=array();
			while ($row = mysqli_fetch_assoc($res)){
				$found = array();
				$found['info_name'] = $row['info_name'];	
				$found['address'] = $row['address'];
				$found['phone_no'] = $row['phone_no'];
				array_push($response["result"],$found);
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
?>
