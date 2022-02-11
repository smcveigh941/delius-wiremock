-- TEAMS
INSERT INTO team(id, team_code, team_description, team_telephone, ldu_code, ldu_description, borough_code, borough_description) VALUES
<<<<<<< Updated upstream
(1, 'cvl', 'Licence Team', '0800001066', 'N55UNA', 'Nottingham City District', 'N55U', 'Nottingham'),
(2, 'pt1', 'Pen Test 1', '0800001066', 'N55UNB', 'Nottingham City District', 'N55U', 'Nottingham'),
(3, 'pt2', 'Pen Test 2', '0800001066', 'N55UNC', 'Nottingham City District', 'N55U', 'Nottingham'),
(4, 'ac', 'Accessibility Testers', '0800001066', 'N55UND', 'Nottingham City District', 'N55U', 'Nottingham'),
(5, 'pb', 'Private Beta Testers', '0800001066', 'N55UNE', 'Nottingham City District', 'N55U', 'Nottingham');

-- STAFF
INSERT INTO staff(id, staff_identifier, username, staff_code, email, telephone_number, staff_forenames, staff_surname, probation_area_code, probation_area_description) VALUES
(1, 2000, null, 'X12346', 'developer@probation.gov.uk', '07786 989777', 'User', 'Developer', 'N55', 'Yorkshire and Humber'),
(2, 3000, 'pt_com1', 'X12347', 'pentester1@probation.gov.uk', '07786 989777', 'Tester', 'Pen1', 'N55', 'Yorkshire and Humber'),
(3, 4000, 'pt_com2', 'X12348', 'pentester2@probation.gov.uk', '07786 989777', 'Tester', 'Pen2', 'N55', 'Yorkshire and Humber'),
(4, 5000, 'ac_com', 'X12349', 'accessibility@probation.gov.uk', '07786 989777', '1', 'Accessibility tester', 'N55', 'Yorkshire and Humber'),
(5, 6000, 'cvl_beta_dev', 'X12345', 'betatester1@probation.gov.uk', '07786 989777','John', 'Smith', 'N55', 'Yorkshire and Humber');
=======
(1, 'cvl', 'Licence Team', '0800001066', 'N55UNA', 'Nottingham City District', 'N55', 'Nottingham'),
(2, 'pb', 'Private Beta Testers', '0800001066', 'N55UNE', 'Nottingham City District', 'N55', 'Nottingham');

-- STAFF
-- The below contact details are not real
INSERT INTO staff(id, staff_identifier, username, staff_code, email, telephone_number, staff_forenames, staff_surname) VALUES
(1, 1000, 'smcveigh2', 'X12340', 'smcveigh2@probation.gov.uk', '07786 989777', 'Stephen', 'McVeigh'),
(2, 2000, 'timharrison', 'X12341', 'timharrison@probation.gov.uk', '07786 989777', 'Tim', 'Harrison'),
(3, 3000, 'cvl_com', 'X12342', 'cvl_com@probation.gov.uk', '07786 989777', 'CVL', 'COM'),
(4, 4000, 'kamsandhu', 'X12343', 'kamsandhu@probation.gov.uk', '07786 989777', 'Kam', 'Sandhu'),
(5, 5000, 'santhosh', 'X12344', 'santhosh@probation.gov.uk', '07786 989777', 'Santhosh', 'Chinnathambi'),
(6, 6000, 'jeddyshaw', 'X12345', 'jeddyshaw@probation.gov.uk', '07786 989777', 'Jonathan', 'Eddyshaw'),
(7, 7000, 'AdamRichardson', 'X12346', 'adamrichardson@probation.gov.uk', '07786 989777', 'Adam', 'Richardson'),
(8, 8000, 'Laura.Jara.Duncan', 'X12347', 'laura.jara.duncan@probation.gov.uk', '07786 989777', 'Laura', 'Jara Duncan'),
(9, 9000, 'cvl_beta_dev', 'X12345', 'betatester1@probation.gov.uk', '07786 989777','John', 'Smith');
>>>>>>> Stashed changes

-- TEAM-STAFF MAP
INSERT INTO staff_team(staff_id, team_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 2);
