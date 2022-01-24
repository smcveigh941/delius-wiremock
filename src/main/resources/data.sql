-- TEAMS
INSERT INTO team(id, team_code, team_description, team_telephone, ldu_code, ldu_description) VALUES
(1, 'cvl', 'Licence Team', '0800001066', 'N55UNA', 'Nottingham City District'),
(2, 'pt1', 'Pen Test 1', '0800001066', 'N55UNB', 'Nottingham City District'),
(3, 'pt2', 'Pen Test 2', '0800001066', 'N55UNC', 'Nottingham City District'),
(4, 'ac', 'Accessibility Testers', '0800001066', 'N55UND', 'Nottingham City District'),
(5, 'pb', 'Private Beta Testers', '0800001066', 'N55UNE', 'Nottingham City District');

-- STAFF
INSERT INTO staff(id, staff_identifier, staff_code, email, telephone_number, staff_forenames, staff_surname) VALUES
(1, 2000, 'X12346', 'developer@probation.gov.uk', '07786 989777', 'User', 'Developer'),
(2, 3000, 'X12347', 'pentester1@probation.gov.uk', '07786 989777', 'Tester', 'Pen1'),
(3, 4000, 'X12348', 'pentester2@probation.gov.uk', '07786 989777', 'Tester', 'Pen2'),
(4, 5000, 'X12349', 'accessibility@probation.gov.uk', '07786 989777', '1', 'Accessibility tester'),
(5, 6000, 'X12345', 'betatester1@probation.gov.uk', '07786 989777','John', 'Smith');

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
