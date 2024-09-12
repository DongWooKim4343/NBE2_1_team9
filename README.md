## 1. 📌 프로젝트 소개 

### 1.1 프로젝트 배경
<img width="500" alt="스크린샷 2024-08-30 오후 2 53 41" src="https://github.com/user-attachments/assets/64d8ad49-66ab-4034-91d1-9ba0f9d8a040">


<br>

- 우리는 작은 로컬 카페 **Grids & Circles** 입니다. 고객들이 Coffe Bean package를 온라인 웹 사이트로 주문합니다. 매일 전날 오후 2시부터 오늘 오후 2시까지의 주문을 모아서 처리합니다.
- 🔐 로그인 기능이 없어 접근성이 높은 대신, 단점을 보완하기 위해 입력받은 이메일 주소를 통해 조회 및 관리가 가능합니다.
    - ⏰ 2시전 기준 같은 이메일로 보낸 주문들은 한번에 처리됩니다.

### 1.2 시스템 구성도
<img width="500" alt="스크린샷 2024-08-30 오후 2 56 01" src="https://github.com/user-attachments/assets/4ebe22bb-a7f5-4554-9e8f-23d6fdd9aeaa">  

### 1.3 팀 소개  
- 팀원 역할
    
    
    | 이름 | 🐱Github🐱 | ✏️역할✏️ |
    | --- | --- | --- |
    | 안홍제(팀장) | https://github.com/hongjeZZ | Order API 제작, 팀장 |
    | 한재재 | https://github.com/HanJae-Jae | Order API 제작 |
    | 강수민 | https://github.com/username0w | Member API 제작 |
    | 김동우(깃허브 관리자) | https://github.com/DongWooKim4343 | Member API 제작, Github 관리 |
    | 박우건 | https://github.com/calmalong | Product API 제작 |
    | 이정우 | https://github.com/jungyoeal | Product API 및 Thymeleaf view 제작 |


### 1.4 개발 환경

- IDE : Intellij
- Language : Java 17
- Build : Gradle
- Framework : Spring Boot 3.3
- Infrastructure : Spring Data JPA, Swagger, Lombok, Web, Validation, Thymeleaf
- RDBMS : MySQL

### 1.5 프로젝트 구조
<details>
  <summary>프로젝트 구조 보기(눌러서 보기)</summary>
  
```java
├── main
│   ├── java
│   │   └── team9
│   │       └── gccoffee
│   │           ├── GcCoffeeApplication.java
│   │           │
│   │           ├── domain
│   │           │   ├── member
│   │           │   │   ├── controller
│   │           │   │   │   └── MemberController.java
│   │           │   │   ├── domain
│   │           │   │   │   ├── Member.java
│   │           │   │   │   └── MemberType.java
│   │           │   │   ├── dto
│   │           │   │   │   ├── MemberPageRequestDTO.java
│   │           │   │   │   ├── MemberRequestDTO.java
│   │           │   │   │   ├── MemberResponseDTO.java
│   │           │   │   │   └── MemberUpdateDTO.java
│   │           │   │   ├── repository
│   │           │   │   │   └── MemberRepository.java
│   │           │   │   └── service
│   │           │   │       ├── MemberService.java
│   │           │   │       └── MemberServiceImpl.java
│   │           │   │
│   │           │   ├── order
│   │           │   │   ├── controller
│   │           │   │   │   └── OrderController.java
│   │           │   │   ├── domain
│   │           │   │   │   ├── Order.java
│   │           │   │   │   ├── OrderItem.java
│   │           │   │   │   └── OrderStatus.java
│   │           │   │   ├── dto
│   │           │   │   │   ├── OrderItemRequest.java
│   │           │   │   │   ├── OrderItemResponse.java
│   │           │   │   │   ├── OrderItemUpdateDTO.java
│   │           │   │   │   ├── OrderPageRequest.java
│   │           │   │   │   ├── OrderRequest.java
│   │           │   │   │   ├── OrderResponse.java
│   │           │   │   │   └── OrderUpdateRequest.java
│   │           │   │   ├── repository
│   │           │   │   │   ├── OrderItemRepository.java
│   │           │   │   │   └── OrderRepository.java
│   │           │   │   └── service
│   │           │   │       └── OrderService.java
│   │           │   │
│   │           │   └── product
│   │           │       ├── controller
│   │           │       │   ├── ProductRestController.java
│   │           │       │   └── ProductViewController.java
│   │           │       ├── domain
│   │           │       │   ├── Category.java
│   │           │       │   └── Product.java
│   │           │       ├── dto
│   │           │       │   ├── ProductRequest.java
│   │           │       │   ├── ProductResponse.java
│   │           │       │   └── ProductUpdateRequest.java
│   │           │       ├── repository
│   │           │       │   └── ProductRepository.java
│   │           │       └── service
│   │           │           ├── ProductService.java
│   │           │           └── ProductServiceImpl.java
│   │           │   
│   │           │   
│   │           └── global
│   │               ├── advice
│   │               │   └── GlobalExceptionHandler.java
│   │               ├── common
│   │               │   └── BaseTimeEntity.java
│   │               ├── config
│   │               │   └── SwaggerOpenAPIConfig.java
│   │               └── exception
│   │                   ├── ErrorCode.java
│   │                   ├── ErrorResponse.java
│   │                   └── GcCoffeeCustomException.java
│   │
│   │
│   └── resources
│       ├── application.yml
│       │
│       ├── static
│       │   └── main.css
│       │
│       └── templates
│           ├── list.html
│           ├── listeach.html
│           └── product.html
│ 
│ 
└─── test
    ├── java
    │   └── team9
    │       └── gccoffee
    │           ├── GcCoffeeApplicationTests.java
    │           └── domain
    │               ├── member
    │               │   ├── repository
    │               │   │   └── MemberRepositoryTests.java
    │               │   └── service
    │               │       └── MemberServiceImplTest.java
    │               ├── order
    │               │   ├── repository
    │               │   │   └── OrderRepositoryTest.java
    │               │   └── service
    │               │       └── OrderServiceTest.java
    │               └── product
    │                   ├── repository
    │                   │   └── ProductRepositoryTest.java
    │                   └── service
    │                       └── ProductServiceImplTest.java
    │ 
    └── resources
        └── application.yml
``` 
</details>


### 1.6 협업 전략  
<details>
  <summary>🌿 브랜치 전략 (눌러서 보기)</summary> 

  - 🌿 브랜치 전략
    
    ![image](https://github.com/user-attachments/assets/f7f94ca2-bbb1-458f-af8b-88007574222b)

    
    Github Flow 전략을 사용합니다.
    
    Github Flow 전략을 사용하여 두 가지 주요 브랜치를 사용합니다. 
    
    🌐 main: 최종 배포 서버 
    
    - 이 브랜치는 항상 배포 가능한 상태를 유지해야 합니다.
        - 오류 없고, 테스트를 통과한 소스코드 만 병합되도록 유지합니다.
    
    🛠️ feature: 개발 서버 브랜치  
    
    - 특정 기능 개발이나 버그 수정을 위해 생성되는 임시 브랜치입니다.
    - feature 브랜치는 보통 개발자가 자신의 fork한 저장소에서 생성하고 작업합니다.
        - 각 기능 또는 버그 수정 작업은 별도의 feature 브랜치에서 진행됩니다.
        - 브랜치는 주로 main  브랜치에서 분기됩니다.
        - 작업이 완료되면, feature 브랜치는  테스트가 완료된 후 main 브랜치로 병합될 수 있습니다.
        - 병합 후, feature 브랜치는 삭제될 수 있습니다.
    
    **❕ 1차 팀 프로젝트에서는 배포가 없으므로, 작업 완료 후 바로 main 브랜치로 PR(Pull Request)을 생성합니다.**  
    
    ✅ PR 승인절차  
    
    - PR(Pull Request)이 생성되면 누군가 한명이 승인해야만 merge가 가능합니다.
    - 별도의 코드리뷰는 하지 않습니다.

  </details>
<details>
  <summary>Git Convention (눌러서 보기)</summary>  
  
| Tag Name          | Description                                                                                       |
|-------------------|---------------------------------------------------------------------------------------------------|
| Feat              | 새로운 기능을 추가                                                                                |
| Fix               | 버그 수정                                                                                         |
| Design            | CSS 등 사용자 UI 디자인 변경                                                                      |
| !BREAKING CHANGE  | 커다란 API 변경의 경우                                                                            |
| !HOTFIX           | 급하게 치명적인 버그를 고쳐야하는 경우                                                            |
| Style             | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우                                             |
| Refactor          | 프로덕션 코드 리팩토링                                                                            |
| Comment           | 필요한 주석 추가 및 변경                                                                          |
| Docs              | 문서 수정                                                                                         |
| Test              | 테스트 코드, 리팩토링 테스트 코드 추가, Production Code 변경 없음                                 |
| Chore             | 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트, Production Code 변경 없음       |
| Rename            | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우                                                |
| Remove            | 파일을 삭제하는 작업만 수행한 경우                                                                |


</details>   

## 2. 기능정리

### 2.1 주요 기능

- 👨‍🦰 Admin
    - 관리자는 상품을 올리기, 수정하기, 삭제하기 가능하다.
    - 관리자는 관리자 코드를 사용해 관리자로 등록이 가능하다.
    - 관리자는 아이디를 사용해 전체 회원 조회가 가능하다.
    - 관리자는 아이디를 사용해 상품 등록이 가능하다.
    - 관리자는 아이디를 사용해 상품 수정이 가능하다.

- 🙎🏻‍♂️ Member
    - 회원이 이메일로 주문 시 회원이 등록된다.
    - 회원은 아이디를 사용해 회원 정보를 조회할 수 있다.
    - 회원은 아이디를 사용해 회원 정보를 수정할 수 있다.
    - 회원은 아이디를 사용해 회원 정보를 삭제할 수 있다.
    - 회원은 전체 상품 조회가 가능하다.
    - 회원은 상품 아이디를 통해 개별 상품을 조회할 수 있다.

- 🧺 Order
    - 관리자는 개별 주문 및 모든 주문을 조회할 수 있다.
    - 고객은 고객 아이디를 통해 주문을 생성할 수 있다.
        - 주문한 상품의 수량이 상품 재고보다 클 경우 주문이 불가능하다.
    - 고객은 고객 아이디를 통해 자신이 주문한 상품 목록을 조회할 수 있다.
    - 고객은 주문 아이디를 통해 주문한 상품의 주소 및 우편 번호를 수정할 수 있다.
    - 고객은 주문 아이템 아이디를 통해 주문 아이템의 수량을 수정할 수 있다.
    - 관리자는 주문을 완료 및 취소할 수 있다.
        - 이미 주문이 완료 되었다면, 완료 및 취소를 할 수 없다.
        - 주문이 취소되면 주문 아이템의 상품 수량이 기존 수량으로 복구된다.
    - 관리자는 취소된 주문만 삭제할 수 있다.
  
## 3. ERD
![image](https://github.com/user-attachments/assets/ca3a0f5e-ac6b-4c63-8106-87c342360162)

<details>  
  <summary>  3.1 DDL 및 관계 설정 (눌러서 보기)</summary>  
  
  관계 설명
  
  - Member - Orders (1:N)
    설명: 한 회원은 여러 개의 주문을 할 수 있습니다.<br>
    비즈니스 규칙: 한 회원이 여러 번 주문할 수 있으므로, 일대다(1:N) 관계로 설정하였습니다.
    
  - Orders - Order_Item (1:N)
    설명: 한 주문은 여러 개의 주문 상품을 포함할 수 있습니다.<br>
    비즈니스 규칙: 하나의 주문에 여러 상품이 포함될 수 있으므로, 일대다(1:N) 관계로 설정하였습니다.
    
   - Product - Order_Item (1:N)  
    설명: 한 상품은 여러 개의 주문 상품에 포함될 수 있습니다.<br>
    비즈니스 규칙: 하나의 상품이 여러 주문에서 주문될 수 있으므로, 일대다(1:N) 관계로 설정하였습니다.
    
    
  - member Table DDL
        
    ```sql
    CREATE TABLE IF NOT EXISTS member ( 
    member_id BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    member_type ENUM('ADMIN', 'CUSTOMER') NULL DEFAULT NULL,
    name VARCHAR(255) NOT NULL,
    postcode VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    modified_at DATETIME(6) NULL DEFAULT NULL,
    PRIMARY KEY (member_id)
    )
    ```
    
 - product Table DDL
        
   ```sql
   CREATE TABLE IF NOT EXISTS product (
   product_id BIGINT NOT NULL AUTO_INCREMENT,
   category ENUM('COFFEE1', 'COFFEE2', 'ETC') NOT NULL,
   description VARCHAR(255) NULL DEFAULT NULL,
   price INT NOT NULL,
   product_name VARCHAR(255) NOT NULL,
   stock_quantity INT NOT NULL,
   created_at DATETIME(6) NOT NULL,
   modified_at DATETIME(6) NULL DEFAULT NULL,
   PRIMARY KEY (product_id)
        )
   ```
    
- orders Table DDL
        
   ```sql
   CREATE TABLE IF NOT EXISTS orders (
   order_id BIGINT NOT NULL AUTO_INCREMENT,
   member_id BIGINT NULL DEFAULT NULL,
   address VARCHAR(255) NOT NULL,
   order_status ENUM('CANCELLED', 'COMPLETED', 'CREATED') NULL DEFAULT NULL,
   postcode VARCHAR(255) NOT NULL,
   created_at DATETIME(6) NOT NULL,
   modified_at DATETIME(6) NULL DEFAULT NULL,
   PRIMARY KEY (order_id),
   FOREIGN KEY (member_id)
   REFERENCES member (member_id)
        )
   ```
    
- order_item Table DDL  
   ```sql
   CREATE TABLE IF NOT EXISTS order_item (
   order_item_id BIGINT NOT NULL AUTO_INCREMENT,
   order_id BIGINT NULL DEFAULT NULL,
   product_id BIGINT NULL DEFAULT NULL,
   category ENUM('COFFEE1', 'COFFEE2', 'ETC') NOT NULL,
   price INT NOT NULL,
   quantity INT NOT NULL,
   created_at DATETIME(6) NOT NULL,
   modified_at DATETIME(6) NULL DEFAULT NULL,
   PRIMARY KEY (order_item_id),
   FOREIGN KEY (product_id)
   REFERENCES product (product_id),
   FOREIGN KEY (order_id)
   REFERENCES testdb.orders (order_id)
      )  
   ```
</details>   


  ## 4. Entity Diagram  
<img width="2060" alt="image (2)" src="https://github.com/user-attachments/assets/a826cdcd-245f-46f5-a97d-0fac74592b41">

<details>  
  <summary>  4.1 Entity 설명 (눌러서 보기)</summary> 

  **1. Member (회원)**
  
  - **Primary Key**: memberId (Long)
  - **Attributes**:
      - name (String)
      - email (String)
      - postcode (String)
      - address (String)
      - memberType (MemberType)
  - **Relationships**:
      - OneToMany 관계로 Order와 연결 (Member는 여러 개의 Order를 가질 수 있음)
      
  
  **2. Order (주문)**
  
  - **Primary Key**: orderId (Long)
  - **Attributes**:
      - orderStatus (OrderStatus)
      - postcode (String)
      - address (String)
  - **Relationships**:
      - ManyToOne 관계로 Member와 연결 (Order는 하나의 Member에 속함)
      - OneToMany 관계로 OrderItem과 연결 (Order는 여러 개의 OrderItem을 가질 수 있음)
  
  **3. OrderItem (주문 항목)**
  
  - **Primary Key**: orderItemId (Long)
  - **Attributes**:
      - category (Category)
      - price (int)
      - quantity (int)
  - **Relationships**:
      - ManyToOne 관계로 Order와 연결 (OrderItem은 하나의 Order에 속함)
      - ManyToOne 관계로 Product와 연결 (OrderItem은 하나의 Product에 속함)
  
  **4. Product (상품)**
  
  - **Primary Key**: productId (Long)
  - **Attributes**:
      - productName (String)
      - category (Category)
      - price (int)
      - description (String)
      - stockQuantity (int)
  - **Relationships**:
      - OneToMany 관계 없음 (주문 항목들과 연결되지만 양방향 관계 설정은 없음)
  
  **연관 관계 설명**
  
  1. **Member와 Order**
      - Member는 여러 개의 Order를 가질 수 있으며, 각각의 Order는 하나의 Member에 속합니다. 즉, 일대다(One-to-Many) 관계입니다.
      - Order 엔티티에서 Member는 ManyToOne으로 설정되어 있어, 주문이 발생할 때마다 해당 회원과 연관됩니다.
      
  2. **Order와 OrderItem**
      - Order는 여러 개의 OrderItem을 가질 수 있으며, 각각의 OrderItem은 하나의 Order에 속합니다. 이 관계 또한 일대다(One-to-Many) 관계입니다.
  
  1. **OrderItem과 Product**
      - OrderItem은 하나의 Product에 속하며, 각각의 주문 항목은 주문된 상품을 나타냅니다. 이는 다대일(Many-to-One) 관계입니다.
      
  2. **기타 비즈니스 로직**
  - **Member**: 사용자 정보(이름, 이메일, 주소 등)를 업데이트할 수 있는 기능을 제공합니다.
  - **Order**: 주문 상태 변경, 주문 취소 등의 기능을 제공합니다. getTotalPrice 메서드를 통해 주문 항목의 총 가격을 계산할 수 있습니다.
  - **OrderItem**: 상품을 등록하거나 수량을 조정할 수 있으며, 주문 취소 시 재고를 복구합니다.
  - **Product**: 재고 관리 및 상품 정보 업데이트 기능을 제공합니다.

</details>

## 5. API 명세서
<details>  
  <summary> API 명세서 (눌러서 보기)</summary> 

| 기능                     | Method | URI                                  | Request Body                                                                                                                                                                                                                                                                                                                                                                    | Response Body                                                                                                                                                                                                                                                                                                                                                                   |  |
|------------------------|--------|--------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
| 회원 등록                 | POST   | /api/v1/members                     | { "name": "string", "email": "string", "postcode": "string", "address": "string", "memberType": "CUSTOMER", "adminCode": "string" }                                                                                                                                                                                                                                             | { "memberId": 1, "name": "string", "email": "string@email.com", "postcode": "string", "address": "string", "memberType": "CUSTOMER" }                                                                                                                                                                                                                                            |   |
| 회원 상세 조회              | GET    | /api/v1/members/{memberId}          | /api/v1/members/{memberId}                                                                                                                                                                                                                                                                                                              | { "memberId": 0, "name": "string", "email": "string", "postcode": "string", "address": "string", "memberType": "CUSTOM" }                                                                                                                                                                                                                                                         |   |
| 회원 전체 조회              | GET    | /api/v1/members/admin/{memberId}    | { "page": 1, "size": 100 }                                                                                                                                                                                                                                                                                                              | [ { "memberId": 0, "name": "string", "email": "string", "postcode": "string", "address": "string", "memberType": "CUSTOMER" } ]                                                                                                                                                                                                                                                  |   |
| 회원 수정                 | PUT    | /api/v1/members/{memberId}          | { "memberId": "12345" }                                                                                                                                                                                                                                                                                                                  | { "id": 0, "name": "string", "email": "string", "postcode": "string", "address": "string" }                                                                                                                                                                                                                                                                                      |   |
| 회원 삭제                 | DELETE | /api/v1/members/{memberId}          | { "memberId": "12345" }                                                                                                                                                                                                                                                                                                                  | { "result": "success" }                                                                                                                                                                                                                                                                                                                                                         |   |
| 주문 생성                 | POST   | /api/v1/orders                      | { "memberId": 0, "orderItemRequests": [ { "productId": 0, "category": "COFFEE1", "price": 1000, "quantity": 5 } ], "postcode": "string", "address": "string" }                                                                                                                                                                                                                   | { "email": "string", "address": "string", "postcode": "string", "orderItemResponses": [ { "productId": 0, "category": "COFFEE1", "price": 0, "quantity": 0 } ], "totalPrice": 0 }                                                                                                                                                                                                                                       |   |
| 관리자 주문 다수 조회          | GET    | /api/v1/orders                      | { "page": 1, "size": 100 }                                                                                                                                                                                                                                                                                                              | [ { "email": "string", "address": "string", "postcode": "string", "orderItemResponses": [ { "productId": 0, "category": "COFFEE1", "price": 0, "quantity": 0 } ], "totalPrice": 0 } ]                                                                                                                                                                                                                                   |   |
| 회원 주문 다수 조회           | GET    | /api/v1/orders/my-order/{memberId}  | Parameters "memberId": "12345" { "page": 1, "size": 100 }                                                                                                                                                                                                                                                                               | [ { "email": "string", "address": "string", "postcode": "string", "orderItemResponses": [ { "productId": 0, "category": "COFFEE1", "price": 0, "quantity": 0 } ], "totalPrice": 0 } ]                                                                                                                                                                                                                                   |   |
| 관리자 개별 주문 조회          | GET    | /api/v1/orders/{orderId}            | { "orderId": "12345" }                                                                                                                                                                                                                                                                                                                   | { "email": "string", "address": "string", "postcode": "string", "orderItemResponses": [ { "productId": 0, "category": "COFFEE1", "price": 0, "quantity": 0 } ], "totalPrice": 0 }                                                                                                                                                                                                                                       |   |
| 고객 주문 수정              | PUT    | /api/v1/orders/{orderId}            | Parameters "orderId": "1" { "postcode": "string", "address": "string" }                                                                                                                                                                                                                                                                  | { "email": "string", "address": "string", "postcode": "string", "orderItemResponses": [ { "productId": 0, "category": "COFFEE1", "price": 0, "quantity": 0 } ], "totalPrice": 0 }                                                                                                                                                                                                                                       |   |
| 관리자 주문 처리            | POST   | /api/v1/orders/{orderId}/complete   | { "orderId": 0 }                                                                                                                                                                                                                                                                                                                         | { "httpStatus: "", "message": "" }                                                                                                                                                                                                                                                                                                                                              |   |
| 고객 주문 취소              | POST   | /api/v1/orders/{orderId}/cancel     | { "orderId": 0 }                                                                                                                                                                                                                                                                                                                         | { "httpStatus": "", "message": "" }                                                                                                                                                                                                                                                                                                                                              |   |
| 고객 주문 삭제              | DELETE | /api/v1/orders/{orderId}            | { "orderId": 0 }                                                                                                                                                                                                                                                                                                                         | { "httpStatus": "", "message": "" }                                                                                                                                                                                                                                                                                                                                              |   |
| 개별 주문 아이템 조회          | GET    | /api/v1/orders/order-item/{orderItemId} | { "orderItemId": 0 }                                                                                                                                                                                                                                                                                                                     | { "productId": 0, "category": "COFFEE1", "price": 0, "quantity": 0 }                                                                                                                                                                                                                                                                                                            |   |
| 개별 주문 아이템 수정          | PUT    | /api/v1/orders/order-item/{orderItemId} | Parameters "orderItemId": 0 { "quantity": 0 }                                                                                                                                                                                                                                                                                             | { "productId": 0, "category": "COFFEE1", "price": 0, "quantity": 0 }                                                                                                                                                                                                                                                                                                            |   |
| 상품 생성                 | POST   | /api/v1/products                    | { "memberId": 0, "productName": "string", "category": "COFFEE1", "price": 0, "description": "string", "stockQuantity": 0 }                                                                                                                                                                                                                 | { "productName": "string", "category": "COFFEE1", "price": 0, "stockQuantity": 0, "description": "string" }                                                                                                                                                                                                                                                                      |   |
| 전체 상품 조회              | GET    | /api/v1/products                    | { "page": 1, "size": 100 }                                                                                                                                                                                                                                                                                                              | [ { "createdAt": "2024-09-12T07:39:35.083Z", "modifiedAt": "2024-09-12T07:39:35.083Z", "productId": 0, "productName": "string", "category": "COFFEE1", "price": 0, "description": "string", "stockQuantity": 0 } ]                                                                                                                                                                                                     |   |
| 개별 상품 조회              | GET    | /api/v1/products/{productId}        | { "productId": 0 }                                                                                                                                                                                                                                                                                                                       | { "productName": "string", "category": "COFFEE1", "price": 0, "stockQuantity": 0, "description": "string" }                                                                                                                                                                                                                                                                      |   |
| 상품 수정                 | PUT    | /api/v1/products/{productId}        | { "productId": 0, "memberId": 0, "productName": "string", "category": "COFFEE1", "price": 0, "description": "string", "stockQuantity": 0 }                                                                                                                                                                                                | { "productName": "string", "category": "COFFEE1", "price": 0, "stockQuantity": 0, "description": "string" }                                                                                                                                                                                                                                                                      |   |
| 상품 삭제                 | DELETE | /api/v1/products/{productId}        | { "productId": 0 }                                                                                                                                                                                                                                                                                                                       | { "httpStatus": "", "message": "" }                                                                                                                                                                                                                                                                                                                                              |   |

</details>  
 




