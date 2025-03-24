## Description:
This test evaluates your ability to develop REST APIs, perform filtering and sorting operations,
and handle JSON in Java. Below, you will find a description of a problem to solve. You are
expected to implement the required solutions and ensure that your code passes all the provided
test cases.

## Instructions:
Implement the following endpoints in Java using your preferred framework (Spring Boot
recommended). The goal is to develop REST services that perform filtering and sorting
operations on a collection of products.
Requirements:
1. **Product Collection**:
- Each product is a JSON object with the following attributes:
- `barcode` (String): The unique identifier of the product.
- `item` (String): The name of the product.
- `category` (String): The product category.
- `price` (Integer): The price of the product.
- `discount` (Integer): The discount percentage on the product.
- `available` (Integer): Availability status (0 for unavailable, 1 for available).
- Example product collection:
```json
[
{
"barcode": "74001755",
"item": "Ball Gown",
"category": "Full Body Outfits",
"price": 3548,
"discount": 7,
"available": 1
},
{
"barcode": "74002423",
"item": "Shawl",
"category": "Accessories",
"price": 758,
"discount": 12,
"available": 1

}
]
```

2. **Endpoints to Implement**:
- **Price Filter**:
- **Endpoint**: `GET /filter/price/{initial_range}/{final_range}`
- **Functionality**: Returns a collection of products whose price is between `initial_range`
  and `final_range`.
- **Response Codes**:
- `200`: Returns an array of products within the price range.
  -`400`: If one parameter is missing or invalid in the specified range, returns an error code
  `400`
- **Sample Response**:
```json
[
{
"barcode": "74001755",
"item": "Ball Gown",
"category": "Full Body Outfits",
"price": 3548,
"discount": 7,
"available": 1
}
]
```
- **Price Sorting**:
- **Endpoint**: `GET /sort/price`
- **Functionality**: Returns a collection of products sorted by price in ascending order.
- **Response Codes**:
- `200`: Returns an array of product names sorted from lowest to highest price.
- **Sample Response**:
```json
[
"Shawl",
"Ball Gown"
]
```
3. **Additional Considerations**:
- The product models are already implemented.
- Implement the services to ensure they pass all provided test cases.

---
### Submission Requirements
1. **Code**: Submit the complete project in a Git repository (GitHub, GitLab, or Bitbucket) and
   share the link.
2. **Documentation**: Include a `README.md` file with:
- An explanation of your solution.
- Instructions on how to run the project and tests.
- Explanation of any significant technical decisions.
3. **Evaluation**: Your solution will be assessed based on:
- Correct functionality of the endpoints.
- Code structure and organization.
- Proper handling of HTTP codes.
- Code quality and clarity.
- Passing all provided test cases.
  Additional Tips
  ● Use of Java 8+ Features: Consider using Java 8 features like Stream API, optional
  parameters, or lambda expressions to improve readability and performance.
  ● Best Practices: Follow RESTful API design principles and best practices for exception
  handling, logging, and unit testing.
  ● Sample Tests: Write a few basic test cases to cover the main scenarios and edge
  cases.

---
**Good luck!**
