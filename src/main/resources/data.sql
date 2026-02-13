-- ===================================================
-- ErasMatch Course Management - Test Data (H2)
-- Αυτό το αρχείο φορτώνεται αυτόματα κάθε φορά που ξεκινάει η εφαρμογή
-- ===================================================

-- Test Users
MERGE INTO users KEY(username) VALUES
    ('host', 'host123', 'host_university', 'host@example.com', 'Test Host Representative'),
    ('testhost', 'test123', 'host_university', 'testhost@example.com', 'Another Host University');

-- Host Universities
MERGE INTO host_universities KEY(userID)
SELECT u.userID, 'Athens University of Economics and Business', 'Greece', 'Athens', 'contact@aueb.gr', '+30 210 8203000'
FROM users u
WHERE u.username = 'host';

MERGE INTO host_universities KEY(userID)
SELECT u.userID, 'Technical University of Munich', 'Germany', 'Munich', 'contact@tum.de', '+49 89 28901'
FROM users u
WHERE u.username = 'testhost';

-- Sample Courses
MERGE INTO courses KEY(hostID, code)
SELECT h.hostID, 'Computer Science', 'Advanced Algorithms', 'CS301', 6, 'Fall'
FROM host_universities h
JOIN users u ON h.userID = u.userID
WHERE u.username = 'host';

MERGE INTO courses KEY(hostID, code)
SELECT h.hostID, 'Computer Science', 'Database Systems', 'CS302', 5, 'Spring'
FROM host_universities h
JOIN users u ON h.userID = u.userID
WHERE u.username = 'host';

MERGE INTO courses KEY(hostID, code)
SELECT h.hostID, 'Mathematics', 'Linear Algebra', 'MATH201', 6, 'Fall'
FROM host_universities h
JOIN users u ON h.userID = u.userID
WHERE u.username = 'host';

MERGE INTO courses KEY(hostID, code)
SELECT h.hostID, 'Economics', 'Microeconomics', 'ECON101', 5, 'Spring'
FROM host_universities h
JOIN users u ON h.userID = u.userID
WHERE u.username = 'host';

MERGE INTO courses KEY(hostID, code)
SELECT h.hostID, 'Business', 'Project Management', 'BUS250', 4, 'Fall'
FROM host_universities h
JOIN users u ON h.userID = u.userID
WHERE u.username = 'host';


