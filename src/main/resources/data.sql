-- TEAMS
INSERT INTO team(id, team_code, team_description, team_telephone, ldu_code, ldu_description, borough_code, borough_description) VALUES
(1, 'cvl', 'Licence Team', '0800001066', 'N55UNA', 'Nottingham City District', 'N55U', 'Nottingham'),
(2, 'pb', 'Private Beta Testers', '0800001066', 'N55UNE', 'Nottingham City District', 'N55U', 'Nottingham');

-- STAFF
-- The below contact details are not real
INSERT INTO staff(id, staff_identifier, username, staff_code, email, telephone_number, staff_forenames, staff_surname, probation_area_code, probation_area_description) VALUES
(1, 1000, 'smcveigh2', 'X12340', 'smcveigh2@probation.gov.uk', '07786 989777', 'Stephen', 'McVeigh', 'N55', 'Yorkshire and Humber'),
(2, 2000, 'timharrison', 'X12341', 'timharrison@probation.gov.uk', '07786 989777', 'Tim', 'Harrison', 'N55', 'Yorkshire and Humber'),
(3, 3000, 'cvl_com', 'X12342', 'cvl_com@probation.gov.uk', '07786 989777', 'CVL', 'COM', 'N55', 'Yorkshire and Humber'),
(4, 4000, 'kamsandhu', 'X12343', 'kamsandhu@probation.gov.uk', '07786 989777', 'Kam', 'Sandhu', 'N55', 'Yorkshire and Humber'),
(5, 5000, 'santhosh', 'X12344', 'santhosh@probation.gov.uk', '07786 989777', 'Santhosh', 'Chinnathambi', 'N55', 'Yorkshire and Humber'),
(6, 6000, 'jeddyshaw', 'X12345', 'jeddyshaw@probation.gov.uk', '07786 989777', 'Jonathan', 'Eddyshaw', 'N55', 'Yorkshire and Humber'),
(7, 7000, 'AdamRichardson', 'X12346', 'adamrichardson@probation.gov.uk', '07786 989777', 'Adam', 'Richardson', 'N55', 'Yorkshire and Humber'),
(8, 8000, 'Laura.Jara.Duncan', 'X12347', 'laura.jara.duncan@probation.gov.uk', '07786 989777', 'Laura', 'Jara Duncan', 'N55', 'Yorkshire and Humber'),
(9, 9000, 'cvl_beta_dev', 'X12348', 'betatester1@probation.gov.uk', '07786 989777','John', 'Smith', 'N55', 'Yorkshire and Humber');

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
