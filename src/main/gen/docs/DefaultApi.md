# DefaultApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addStockMovements**](DefaultApi.md#addStockMovements) | **POST** /ingredients/{id}/stockMovements | Ajoute des mouvements de stock à un ingrédient |
| [**getAllDishes**](DefaultApi.md#getAllDishes) | **GET** /dishes | Retourne la liste de tous les plats avec leurs ingrédients |
| [**getAllIngredients**](DefaultApi.md#getAllIngredients) | **GET** /ingredients | Retourne la liste de tous les ingrédients |
| [**getIngredientById**](DefaultApi.md#getIngredientById) | **GET** /ingredients/{id} | Retourne un ingrédient par son identifiant |
| [**getIngredientStock**](DefaultApi.md#getIngredientStock) | **GET** /ingredients/{id}/stock | Retourne la valeur de stock d&#39;un ingrédient à un instant donné |
| [**getStockMovements**](DefaultApi.md#getStockMovements) | **GET** /ingredients/{id}/stockMovements | Retourne les mouvements de stock d&#39;un ingrédient filtrés par plage de date |
| [**updateDishIngredients**](DefaultApi.md#updateDishIngredients) | **PUT** /dishes/{id}/ingredients | Modifie la liste des ingrédients associés à un plat |


<a id="addStockMovements"></a>
# **addStockMovements**
> List&lt;StockMovement&gt; addStockMovements(id, createStockMovement)

Ajoute des mouvements de stock à un ingrédient

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | Identifiant de l'ingrédient
    List<CreateStockMovement> createStockMovement = Arrays.asList(); // List<CreateStockMovement> | Liste des mouvements de stock à créer
    try {
      List<StockMovement> result = apiInstance.addStockMovements(id, createStockMovement);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#addStockMovements");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| Identifiant de l&#39;ingrédient | |
| **createStockMovement** | [**List&lt;CreateStockMovement&gt;**](CreateStockMovement.md)| Liste des mouvements de stock à créer | |

### Return type

[**List&lt;StockMovement&gt;**](StockMovement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Mouvements de stock créés avec succès |  -  |
| **400** | Corps de la requête manquant ou vide |  -  |
| **404** | Ingrédient non trouvé |  -  |

<a id="getAllDishes"></a>
# **getAllDishes**
> List&lt;Dish&gt; getAllDishes()

Retourne la liste de tous les plats avec leurs ingrédients

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      List<Dish> result = apiInstance.getAllDishes();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getAllDishes");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Dish&gt;**](Dish.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des plats retournée avec succès |  -  |

<a id="getAllIngredients"></a>
# **getAllIngredients**
> List&lt;Ingredient&gt; getAllIngredients()

Retourne la liste de tous les ingrédients

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      List<Ingredient> result = apiInstance.getAllIngredients();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getAllIngredients");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Ingredient&gt;**](Ingredient.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des ingrédients retournée avec succès |  -  |

<a id="getIngredientById"></a>
# **getIngredientById**
> Ingredient getIngredientById(id)

Retourne un ingrédient par son identifiant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | Identifiant de l'ingrédient
    try {
      Ingredient result = apiInstance.getIngredientById(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getIngredientById");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| Identifiant de l&#39;ingrédient | |

### Return type

[**Ingredient**](Ingredient.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ingrédient retourné avec succès |  -  |
| **404** | Ingrédient non trouvé |  -  |

<a id="getIngredientStock"></a>
# **getIngredientStock**
> StockValue getIngredientStock(id, at, unit)

Retourne la valeur de stock d&#39;un ingrédient à un instant donné

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | Identifiant de l'ingrédient
    OffsetDateTime at = OffsetDateTime.parse("2026-01-02T12:00Z"); // OffsetDateTime | Instant auquel récupérer la valeur du stock (format ISO-8601)
    UnitType unit = UnitType.fromValue("KG"); // UnitType | Unité du stock à récupérer
    try {
      StockValue result = apiInstance.getIngredientStock(id, at, unit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getIngredientStock");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| Identifiant de l&#39;ingrédient | |
| **at** | **OffsetDateTime**| Instant auquel récupérer la valeur du stock (format ISO-8601) | |
| **unit** | [**UnitType**](.md)| Unité du stock à récupérer | [enum: KG, PCS, L] |

### Return type

[**StockValue**](StockValue.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Valeur de stock retournée avec succès |  -  |
| **400** | Paramètre obligatoire manquant |  -  |
| **404** | Ingrédient non trouvé |  -  |

<a id="getStockMovements"></a>
# **getStockMovements**
> List&lt;StockMovement&gt; getStockMovements(id, from, to)

Retourne les mouvements de stock d&#39;un ingrédient filtrés par plage de date

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | Identifiant de l'ingrédient
    OffsetDateTime from = OffsetDateTime.parse("2026-01-01T00:00Z"); // OffsetDateTime | Début de la plage de date (format ISO-8601)
    OffsetDateTime to = OffsetDateTime.parse("2026-03-31T23:59:59Z"); // OffsetDateTime | Fin de la plage de date (format ISO-8601)
    try {
      List<StockMovement> result = apiInstance.getStockMovements(id, from, to);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getStockMovements");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| Identifiant de l&#39;ingrédient | |
| **from** | **OffsetDateTime**| Début de la plage de date (format ISO-8601) | |
| **to** | **OffsetDateTime**| Fin de la plage de date (format ISO-8601) | |

### Return type

[**List&lt;StockMovement&gt;**](StockMovement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des mouvements de stock retournée avec succès |  -  |
| **400** | Paramètre obligatoire manquant |  -  |
| **404** | Ingrédient non trouvé |  -  |

<a id="updateDishIngredients"></a>
# **updateDishIngredients**
> Dish updateDishIngredients(id, ingredient)

Modifie la liste des ingrédients associés à un plat

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | Identifiant du plat
    List<Ingredient> ingredient = Arrays.asList(); // List<Ingredient> | Liste des ingrédients à associer au plat
    try {
      Dish result = apiInstance.updateDishIngredients(id, ingredient);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#updateDishIngredients");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| Identifiant du plat | |
| **ingredient** | [**List&lt;Ingredient&gt;**](Ingredient.md)| Liste des ingrédients à associer au plat | |

### Return type

[**Dish**](Dish.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des ingrédients du plat mise à jour avec succès |  -  |
| **400** | Corps de la requête manquant |  -  |
| **404** | Plat non trouvé |  -  |

