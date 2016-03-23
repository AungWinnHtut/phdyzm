<?php
    $myfile = fopen("testfile.txt", "a") or die("Unable to open file!");
    fwrite($myfile, "testing\n");
    echo "success appending file";
    fclose($myfile);
?>
