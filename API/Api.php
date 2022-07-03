<?php 
    //getting the database connection
    require_once 'DbConnect.php';
 
    //an array to display response
    $response = array();
 
    //if it is an api call 
    //that means a get parameter named api call is set in the URL 
    //and with this parameter we are concluding that it is an api call 
    if(isset($_GET['apicall'])){
 
      switch($_GET['apicall']){
   
        case 'signup':
          //checking the parameters required are available or not 
          if(isTheseParametersAvailable(array('username','email','password'))){
     
            //getting the values 
            $username = $_POST['username']; 
            $email = $_POST['email']; 
            $password = md5($_POST['password']);
       
       
            //checking if the user is already exist with this username or email
            //as the email and username should be unique for every user 
            $stmt = $conn->prepare("SELECT id FROM users WHERE username = ? OR email = ?");
            $stmt->bind_param("ss", $username, $email);
            $stmt->execute();
            $stmt->store_result();
       
            //if the user already exist in the database 
            if($stmt->num_rows > 0){
             $response['error'] = true;
             $response['message'] = 'User already registered';
             $stmt->close();
            }
            else{
              //if user is new creating an insert query 
              $stmt = $conn->prepare("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
              $stmt->bind_param("sss", $username, $email, $password);
              //if the user is successfully added to the database 
              if($stmt->execute()){
                //fetching the user back 
                $stmt = $conn->prepare("SELECT id, username, email,TARGET,week FROM users WHERE username = ?"); 
                $stmt->bind_param("s",$username);
                $stmt->execute();
                $stmt->bind_result($id, $username, $email,$target,$week);
                $stmt->fetch();
               
                $user = array(
                'id'=>$id, 
                'username'=>$username, 
                'email'=>$email,
                'target'=>$target,
		    'week'=>$week
                );
               
                $stmt->close();
               
                //adding the user data in response 
                $response['error'] = false; 
                $response['message'] = 'User registered successfully'; 
                $response['user'] = $user; 
              }
            }
     
          }
          else{
            $response['error'] = true; 
            $response['message'] = 'required parameters are not available'; 
          }
   
        break; 
   
        case 'login':
         //for login we need the username and password 
         if(isTheseParametersAvailable(array('username', 'password'))){
           //getting values 
           $username = $_POST['username'];
           $password = md5($_POST['password']); 
           
           //creating the query 
           $stmt = $conn->prepare("SELECT id, username, email,TARGET,week FROM users WHERE username = ? AND password = ?");
           $stmt->bind_param("ss",$username, $password);
           
           $stmt->execute();
           
           $stmt->store_result();
     
           //if the user exist with given credentials 
           if($stmt->num_rows > 0){
     
              $stmt->bind_result($id, $username, $email,$target,$week);
             $stmt->fetch();
             
             $user = array(
             'id'=>$id, 
             'username'=>$username, 
             'email'=>$email,
             'target'=>$target,
		 'week'=>$week
             );
             
             $response['error'] = false; 
             $response['message'] = 'Login successfull'; 
             $response['user'] = $user; 
            }
            else{
             //if the user not found 
             $response['error'] = true; 
             $response['message'] = 'Invalid username or password';
            }
          }
          
        case 'update':
          if(isTheseParametersAvailable(array('id','parity', 'mother_age', 'mother_height', 'mid_arm_cir','workload','anemia', 'asthma', 'bad_obs_history', 'injection', 'falif','iron', 'workload2', 'convolusion','bleed','asthma2','inject2', 'iron2', 'convolusion2', 'bleed2','fever','BMI'))){
            $id = $_POST['id'];
            $parity = $_POST['parity'];
            $mother_age = $_POST['mother_age'];
            $mother_height = $_POST['mother_height'];
            $mid_arm_cir = $_POST['mid_arm_cir'];
            $workload = $_POST['workload'];
            $anemia = $_POST['anemia'];
            $asthma = $_POST['asthma'];
            $bad_obs_history = $_POST['bad_obs_history'];
            $injection = $_POST['injection'];
            $falif = $_POST['falif'];
            $iron = $_POST['iron'];
            $workload2 = $_POST['workload2'];
            $convolusion = $_POST['convolusion'];
            $bleed = $_POST['bleed'];
            $asthma2 = $_POST['asthma2'];
            $injection2 = $_POST['inject2'];
            $iron2 = $_POST['iron2'];
            $convolusion2 = $_POST['convolusion2'];
            $bleed2 = $_POST['bleed2'];
            $fever = $_POST['fever'];
            $bmi = $_POST['BMI'];
            
            $stmt = $conn->prepare("UPDATE users SET parity=?, mother_age=?, mother_height=?, mid_arm_cir=?,workload=?,anemia=?, asthma=?, bad_obs_history=?, injection=?, falif=?,iron=?, 2nd_workload=?, 1st_convolusion=?,bleed1=?,asthma2=?,inject2=?, iron2=?, 2nd_convolusion=?, bleed=?,fever=?,BMI=? WHERE id=?");
            $stmt->bind_param("ssssssssssssssssssssss", $parity,$mother_age,$mother_height,$mid_arm_cir,$workload,$anemia,$asthma,$bad_obs_history,$injection,$falif,$iron,$workload2,$convolusion,$bleed,$asthma2,$injection2,$iron2,$convolusion2,$bleed2,$fever,$bmi,$id);
            if($stmt->execute()){
		  $stmt->store_result();
              $response['error'] = false; 
              $response['message'] = 'Updated successfully';
            }
            else{
              $response['error'] = true; 
              $response['message'] = 'Unexpected error occurred';
            }
          }
          
        break; 

	 case 'refresh':
          if(isTheseParametersAvailable(array('id'))){
            $id = $_POST['id'];
            
            $stmt = $conn->prepare("SELECT id, username, email,TARGET,week FROM users WHERE id = ?"); 
            $stmt->bind_param("s",$id);
            $stmt->execute();
            $stmt->bind_result($userid,$username,$email,$target,$week);
            $stmt->fetch();
               
            $user = array(
            'id'=>$userid, 
            'username'=>$username, 
            'email'=>$email,
            'target'=>$target,
		'week'=>$week
            );
               
            $stmt->close();
            
            //adding the user data in response 
            $response['error'] = false; 
            $response['message'] = 'User refreshed successfully'; 
            $response['user'] = $user;
          }
          else{
            $response['error'] = true; 
            $response['message'] = 'Unexpected error occurred';
          }
          
        break;

	case 'week':
          if(isTheseParametersAvailable(array('id','week','date'))){
            $id = $_POST['id'];
            $week = $_POST['week'];
		$date = $_POST['date'];
            
            $stmt = $conn->prepare("UPDATE users SET week=?,date=? WHERE id=?");
            $stmt->bind_param("sss",$week,$date,$id);
            if($stmt->execute()){
              $stmt->store_result();
              $stmt = $conn->prepare("SELECT id, username, email,TARGET,week FROM users WHERE id = ?"); 
              $stmt->bind_param("s",$id);
              $stmt->execute();
              $stmt->bind_result($userid,$username,$email,$target,$week);
              $stmt->fetch();
               
              $user = array(
              'id'=>$userid, 
              'username'=>$username, 
              'email'=>$email,
              'target'=>$target,
              'week'=>$week
              );
               
              $stmt->close();
            
             //adding the user data in response 
              $response['error'] = false; 
              $response['message'] = 'Week updated successfully'; 
              $response['user'] = $user;
            }
            else{
              $response['error'] = true; 
              $response['message'] = 'Unexpected error occurred';
            }
          }
          else{
            $response['error'] = true; 
            $response['message'] = 'Unexpected error occurred';
          }
          
        break;

case 'food':
          if(isTheseParametersAvailable(array('week'))){
            $week = $_POST['week'];
            
            $stmt = $conn->prepare("SELECT Items FROM food WHERE Week = ?"); 
            $stmt->bind_param("s",$week);
            $stmt->execute();
            $stmt->bind_result($items);
            $stmt->fetch();
            $stmt->close();
            
            //adding the data in response 
            $response['error'] = false; 
            $response['message'] = 'Items fetched successfully'; 
            $response['food'] = $items;
          }
          else{
            $response['error'] = true; 
            $response['message'] = 'Unexpected error occurred';
          }
          
        break;

case 'exc':
          if(isTheseParametersAvailable(array('week'))){
            $week = $_POST['week'];
            
            $stmt = $conn->prepare("SELECT items FROM exercises WHERE Week = ?"); 
            $stmt->bind_param("s",$week);
            $stmt->execute();
            $stmt->bind_result($items);
            $stmt->fetch();
            $stmt->close();
            
            //adding the data in response 
            $response['error'] = false; 
            $response['message'] = 'Items fetched successfully'; 
            $response['exc'] = $items;
          }
          else{
            $response['error'] = true; 
            $response['message'] = 'Unexpected error occurred';
          }
          
        break;

case 'date':
          if(isTheseParametersAvailable(array('id'))){
            $id = $_POST['id'];
           
              $stmt = $conn->prepare("SELECT date FROM users WHERE id = ?"); 
              $stmt->bind_param("s",$id);
              $stmt->execute();
              $stmt->bind_result($date);
              $stmt->fetch();
		  $stmt->close();

      
            
             //adding the user data in response 
              $response['error'] = false; 
              $response['message'] = 'date fetched successfully'; 
              $response['date'] = $date;
          }
          else{
            $response['error'] = true; 
            $response['message'] = 'Unexpected error occurred';
          }
          
        break;
   
        default: 
          $response['error'] = true; 
          $response['message'] = 'Invalid Operation Called';
      }
 
    }
    else{
     //if it is not api call 
     //pushing appropriate values to response array 
     $response['error'] = true; 
     $response['message'] = 'Invalid API Call';
    }
 
    //displaying the response in json structure 
    echo json_encode($response);
    //function validating all the paramters are available
    //we will pass the required parameters to this function 
    function isTheseParametersAvailable($params){
 
      //traversing through all the parameters 
      foreach($params as $param){
        //if the paramter is not available
        if(!isset($_POST[$param])){
          //return false 
          return false; 
        }
      }
    //return true if every param is available 
    return true; 
    }
?>


