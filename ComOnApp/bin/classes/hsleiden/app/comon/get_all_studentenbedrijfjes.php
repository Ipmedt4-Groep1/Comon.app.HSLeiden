<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT *FROM deelnemers ") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["deelnemers"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $deelnemers = array();
		$deelnemers ["pid"] = $row["pid"];
        $deelnemers	["naam"] = $row["naam"];
		$deelnemers	["logo"] = $row["logo"];
		$deelnemers	["deelnemers"] = $row["deelnemers"];
        		
     
 
        // push single product into final response array
        array_push($response["deelnemers"], $deelnemers);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No Deelnemers found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>