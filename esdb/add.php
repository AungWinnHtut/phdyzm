<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>add</title>
</head>

<body>
<?php 
include_once("config.php"); 
if (isset($_POST['submit'])):
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
		
		if ($res_hotel) { echo "Added!";}
endif

?>
<form method="post" action="">
<label for="cat">Select Catalog : </label><select name="catalog"  >
<?php 
$sql = "SELECT * FROM category";
$res = mysqli_query($conn, $sql );
while ($row = mysqli_fetch_assoc($res)):
	$cat_id = $row['catid'];
	$name = $row['name'];
	echo "<option value='$cat_id'>$name</option>";
endwhile
 ?>
</select><br>
<label for="name">Name : </label><input name="name" type="text"><br>
<label for="address">Address : </label><textarea name="address" cols="15" rows="1"></textarea><br>
<label for="phone_no">Phone No : </label><input name="phone_no" type="text"><br>
<label for="url"> URL :   </label><input name="url" type="text"><br>
<label for="rating">Rating : </label><select name="rating"  >
<option>1</option>
<option>2</option>
<option>3</option>
<option>4</option>
<option>5</option>
</select>
<br>
<label for="lat"> Lat :   </label><input name="lat" type="text"><br>
<label for="lng"> Lng :   </label><input name="lng" type="text"><br>
<label for="ph"> Price High :   </label><input name="ph" type="text"><br>
<label for="pl"> Price Low :   </label><input name="pl" type="text"><br>
<label for="cu"> Cuisine :   </label><input name="cu" type="text"><br>
<input type="submit" name="submit">
</form>
</body>
</html>