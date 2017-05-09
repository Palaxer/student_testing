INSERT INTO `student_testing_db_test`.`role` VALUES (3,'ADMIN'),(1,'STUDENT'),(2,'TUTOR');
INSERT INTO `student_testing_db_test`.`category` VALUES (1,'name1'),(2,'name2');
INSERT INTO `student_testing_db_test`.`user` VALUES (1,'login1','passwd1','name1','surname1',3),(2,'login2','passwd2','name2','surname2',2),(3,'login3','passwd3','name3','surname3',3),(4,'login4','passwd4','name4','surname4',1);
INSERT INTO `student_testing_db_test`.`test` VALUES (1,'name1','desc1',1,1,10,10,1),(2,'name2','desc2',3,2,10,10,1),(3,'name3','desc3',1,1,10,10,0);
INSERT INTO `student_testing_db_test`.`question` VALUES (1,'text1',1),(2,'text2',2), (3,'text3',1);
INSERT INTO `student_testing_db_test`.`answer` VALUES (1,'text1',1,1), (2,'text2',1,2);
INSERT INTO `student_testing_db_test`.`complete_test` VALUES (1,10,'2017-05-08 00:00:00',10,4,1,1), (2,10,'2017-05-08 00:00:00',10,1,1,0);