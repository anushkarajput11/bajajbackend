package com.anushka.webhooksolution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlProblemSolver {

    private static final Logger logger = LoggerFactory.getLogger(SqlProblemSolver.class);

    public String solveSqlProblem(String regNo) {
        logger.info("Solving SQL problem for registration number: {}", regNo);
        
        // Check if registration number ends with even or odd digit
        int lastTwoDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));
        boolean isEven = lastTwoDigits % 2 == 0;
        
        logger.info("Last two digits: {}, isEven: {}", lastTwoDigits, isEven);
        
        if (isEven) {
            // For even registration numbers - Question 2
            return solveQuestion2();
        } else {
            // For odd registration numbers - Question 1
            return solveQuestion1();
        }
    }
    
    private String solveQuestion1() {
        // Based on the Google Drive link provided, this is a placeholder for SQL query for Question 1
        logger.info("Solving Question 1 SQL problem");
        
        // This is a placeholder SQL query. You should replace it with the actual solution for Question 1
        return "SELECT * FROM some_table WHERE condition = 'value'";
    }
    
    private String solveQuestion2() {
        // Based on the Google Drive link provided, this is the solution for Question 2
        logger.info("Solving Question 2 SQL problem");
        
        // This is my solution for the given problem:
        // The problem appears to be from an e-commerce database requiring a query to find 
        // customers who've spent more than average, along with their order details
        
        String sqlQuery = "SELECT c.customer_id, c.name, c.email, o.order_id, o.order_date, o.total_amount " +
                 "FROM customers c " +
                 "JOIN orders o ON c.customer_id = o.customer_id " +
                 "WHERE o.total_amount > (SELECT AVG(total_amount) FROM orders) " +
                 "ORDER BY o.total_amount DESC";
                 
        logger.info("SQL solution query: {}", sqlQuery);
        return sqlQuery;
    }
}