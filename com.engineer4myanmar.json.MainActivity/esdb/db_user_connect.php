<?php

/**
 * A class file to connect to database
 */
class DB_CONNECT {
    var $myconn;
    // constructor
    // function __construct() {
    //     // connecting to database
    //     $this->connect();
    // }

    // // destructor
    // function __destruct() {
    //     // closing db connection
    //     $this->close();
    // }

    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        require_once __DIR__ . '/db_user_config.php';

        // Connecting to mysql database
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysqli_error($con));
       $this->myconn = $con;


 //$con = mysqli_connect("127.0.0.1","admin","","esdb") or die(mysqli_error($con));
       // $this->myconn = $con;

        // returing connection cursor
        return $this->myconn;
    }

    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
        mysqli_close($myconn);
    }

}

?>