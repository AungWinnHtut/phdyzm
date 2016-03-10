<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>search</title>
</head>

<body>

<form method="post" action="">
<label for="cat">Select Catalog : </label><select name="catalog"  >
<?php 
include_once("config.php");
$sql = "SELECT * FROM category";
$res = mysqli_query($conn, $sql );
while ($row = mysqli_fetch_assoc($res)):
	$cat_id = $row['catid'];
	$name = $row['name'];
	echo "<option value='$name'>$name</option>";
endwhile
 ?>
</select><br>
  <label for="rating">Rating</label>
  <select name="rating" id="select">
  	<option>1 </option>
    <option>2 </option>
    <option>3 </option>
    <option>4 </option>
    <option>5 </option>
  </select><br>
  <label for="price">Price</label>
  From :<input type="text" name="p1"/> To : <input type="text" name="p2"/><br>
  <label for="cuisine">Cuisine</label>
  <select name="cuisine" id="select">
  	<option>Chinese</option>
    <option>Thai</option>
    <option>Burmese </option>
    <option>Japanese</option>
  </select><br>

  <input type="submit" value="search">
</form>
<br>
<?php

	
	if (isset($_POST['rating'])){
		$catalog = $_POST['catalog'];
		$rating =$_POST['rating'];
		$p1 = $_POST['p1'];
		$p2 = $_POST['p2'];	
		$cui = $_POST['cuisine'];
		//FROM cat INNER JOIN book ON cat.ID = book.cat_id GROUP BY cat.name;
		
		//$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%' OR  BETWEEN $catalog.price_high=$p2 AND $catalog.price_low=$p1  GROUP BY information.name "  ;
		$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%'  AND  $p1 BETWEEN $catalog.price_high AND $catalog.price_low AND   $p2 BETWEEN $catalog.price_high AND $catalog.price_low   GROUP BY information.name "  ;
		//$sql = "SELECT information.name AS info_name, $catalog.*,information.* FROM information inner join $catalog on information.id= $catalog.info_id WHERE information.rating like '%$rating%'  OR $catalog.cuisine like '%$cui%' AND  $p1 = $catalog.price_low AND   $p2 = $catalog.price_high  GROUP BY information.name "  ;
		
		//echo $sql;
		$res = mysqli_query($conn,$sql) or die(mysqli_error());
		$row = mysqli_num_rows($res);
		if($row> 0){
			//echo "OL";
			while ($row = mysqli_fetch_assoc($res)){
				$name = $row['info_name'];	
				$address = $row['address'];
				$phone_no = $row['phone_no'];
					
				echo "<b>".$name. "</b>&nbsp;". $address."&nbsp;".$phone_no;	
				echo 	"<br>";
			}
		}
		
	}
?>
</body>
</html>