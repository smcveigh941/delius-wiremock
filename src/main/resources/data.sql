-- TEAMS
INSERT INTO team(id, team_code, team_description, team_telephone, ldu_code, ldu_description, borough_code, borough_description) VALUES
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

-- TEAM-STAFF MAP
INSERT INTO staff_team(team_id, staff_id) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 3),
(4, 1),
(4, 4),
(5, 1),
(5, 5);
