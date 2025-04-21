<?php
$host = "localhost";
$db = "student_app";
$user = "your_user";
$pass = "your_password";

$conn = new mysqli($host, $user, $pass, $db);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$email = $_POST['email'];
$password = password_hash($_POST['password'], PASSWORD_DEFAULT);
$student_number = $_POST['student_number'];

$stmt = $conn->prepare("INSERT INTO students (email, password, student_number) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $email, $password, $student_number);

if ($stmt->execute()) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error", "message" => $stmt->error]);
}

$stmt->close();
$conn->close();
?>
