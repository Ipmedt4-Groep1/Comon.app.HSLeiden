<?php
 
/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["pid"])) {
    $pid = $_GET['pid'];
 
    // get a product from products table
    $result = mysql_query("SELECT *FROM studentenbedrijfjes WHERE pid = $pid");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $studentenbedrijfjes = array();
            $studentenbedrijfjes["pid"] = $result["pid"];
            $studentenbedrijfjes["naam"] = $result["naam"];
			$studentenbedrijfjes["soort"] = $result["soort"];
			$studentenbedrijfjes["opdrachtgever"] = $result["opdrachtgever"];
			$studentenbedrijfjes["beschrijving"] = $result["beschrijving"];
			$studentenbedrijfjes["deelnemers"] = $result["deelnemers"];
			$studentenbedrijfjes["website"] = $result["website"];
			$studentenbedrijfjes["likes"] = $result["likes"];
          
            // success
            $response["success"] = 1;
 
            // user node
            $response["studentenbedrijfjes"] = array();
 
            array_push($response["studentenbedrijfjes"], $studentenbedrijfjes);
 
                    // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>