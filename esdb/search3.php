<?php
	
	//Test function to check location range
	//function checkGPS(long x0, long y0,int r,long x1, long y1){
	//	int x=0,y=r;
		//xy xs=x+x0; ys=y+y0;
	//	int p=1-r;
	//	while(x<y){
	//		x++;
	//		if (p<0) p+=2*x+1;
	//		else{
	//			y--;
	//			p+=2*(x-y)+1
	//		}

			//xy xs=x+x0; ys=y+y0;
	//	}

	//}


		
	// include db connect class
	require_once __DIR__ . '/db_user_connect.php';

	// connecting to db
	$con = new DB_CONNECT();
	$con->connect();
	/////////////////////////////////////
	//	$sql = "SELECT * FROM category";
	//	$res = mysqli_query($con->myconn, $sql );
	//	while ($row = mysqli_fetch_assoc($res)){
	//		$cat_id = $row['catid'];
	//		$name = $row['name'];		
	//}
	///////////////////////////////////
	if (isset($_GET['catalog'])){
		//Read input data according to their name and save in variables
		$catalog = $_GET['catalog'];
		$rating =$_GET['rating'];
		$p1 = $_GET['p1'];
		$p2 = $_GET['p2'];	
		$cui = $_GET['cuisine'];
		//	TO DO * add range, x0, y0 here *** need to add in android to ask
		//	$range = $_GET['range'];
		//	$cur_lat = $_GET['cur_lat'];
		//	$cur_lng = $_GET['cur_lng'];
		//  if cur_lat and cur_lng = 0 then default value is 22.023966, 96.447359

		//	sample data
		//	$catalog = "hotel";
		//	$rating ="2";
		//	$p1 = "10";
		//	$p2 = "1000";	
		//	$cui = "chinese";	


		//1-Debug information save in search3.log (input parameters)
		$myfile = fopen("search3.log", "w") or die("Unable to open file!");
    	fwrite($myfile, "\ninputs cat_name=".$catalog." rating=".$rating." p1=".$p1." cursine=".$cui." \n");
    	fclose($myfile);

			
		
		//$sql = "SELECT information.name AS info_name, $catalog.*,information.* 
		//FROM information inner join $catalog on information.id= $catalog.info_id 
		//WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%' 
		//OR  BETWEEN $catalog.price_high=$p2 AND $catalog.price_low=$p1  
		//GROUP BY information.name "  ;

		
		$sql = "SELECT information.name AS 
					info_name, 
					$catalog.*,
					information.* 

				FROM 
					information 
				inner join 
					$catalog 
				on 
					information.id= $catalog.info_id 
				WHERE 
					information.rating like '%$rating%'  
					OR $catalog.cuisine like '%$cui%'  
					AND  $p1 BETWEEN $catalog.price_high 
					AND $catalog.price_low 
					AND   $p2 BETWEEN $catalog.price_high 
					AND $catalog.price_low   
				GROUP BY 
					information.name "  ;
		
		//2-Debug information save in search3.log (sql string)
		$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\n sql".$sql." \n");
    	fclose($myfile);

		//Starting query
		$res = mysqli_query($con->myconn,$sql) or die(mysqli_error());
		
		//Extracting rows from result
		$row = mysqli_num_rows($res);

		//3-Debug information save in search3.log (rows from result)
		$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    	fwrite($myfile, "\n Roll  ".$row." \n");
    	fclose($myfile);

    	//if there is data?
		if($row> 0){
			
			//if the lat and long are valid???
			//4-Debug information save in search3.log (flag about data exist)
			$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    		fwrite($myfile, "\n data exist \n");    			
    		fclose($myfile);

    		//create two dimensional array for every data resulted
			$response["result"]=array(); 
			while ($row = mysqli_fetch_assoc($res)){
				//create new empty array to store data from one row
				$found = array(); 
				//save every column data from one row in empty array found
				//first finding lat and lng
				$latitute = $row['lat'];
				$Longitude = $row['lng'];

				//if(checkRange($latitute,$Longitude,$range,$cur_lat,$cur_lng)){

				$found['lat'] = $Latitute;
				$found['lng'] = $Longitude;

				$found['info_name'] = $row['info_name'];	
				$found['address'] = $row['address'];
				$found['phone_no'] = $row['phone_no'];

				//}else{//Do Nothing} //skip these data


				//save found array into another two dimensional array result
				array_push($response["result"],$found);

				//5-Debug information save in search3.log 
				$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    			fwrite($myfile, "\nInfoname=".$row['info_name']." Address=".
    							$row['address']." Phone no=".$row['phone_no'].
    							"\nLat=".$row['lat']." Lng=".$row['lng']." \n");
    			fclose($myfile);

			}

			//Add success flag 1 into two dimentional array response
			$response["success"]=1;

			//Creat Final JSON by encoding two dimentional array response and ECHO it
			echo json_encode($response);
		}
		else{
			//if no rows or data success Flags =0 and add to two dimentional array $response
			$response["success"]=0;
			//add error message in two dimentional array $response
			$response["message"]="Not found";
			//Creat Final JSON by encoding two dimentional array response and ECHO it 
			//(only two error code included)
			echo json_encode($response);
		}
		
	}
	

	// check for empty result from query
		if ($res){
    		// success
    		$response["success"] = 1;
    		// echoing JSON response
    		echo json_encode($response);
		} else {
			$myfile = fopen("search3.log", "a") or die("Unable to open file!");
    		fwrite($myfile, "\nno inputs \n");    		
    		fclose($myfile);

    		// no logins found
    		$response["success"] = 0;
    		$response["message"] = "No detail found";
    		// echo no users JSON
    		echo json_encode($response);
		}
?>
