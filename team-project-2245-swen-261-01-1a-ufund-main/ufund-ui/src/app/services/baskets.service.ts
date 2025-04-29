/**
 * Service for handling API interactions related to user's individual baskets
 * Provides methods to retrieve, search, create, update, and delete baskets.
 *
 * Author[s]: Emma Wheeler
 * Major credit to Mary, most of this is just refactoring from her need.service.ts
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Interface representing a Basket.
 */
export interface Basket {
  id?: number;        // Unique identifier for the basket (optional)
  username: string;       // Name of the basket
  items: number[];       // list of need id's 
  counts: number[];    //Count of items in basket
}

@Injectable({
  providedIn: 'root'
})
export class BasketsService {
  private apiUrl = 'http://localhost:8080/baskets'; //may need to be changed. i don't really know this stuff too well

  constructor(private http: HttpClient) {}

  /**
   * Retrieves all basket.
   * @returns Observable of an array of baskets.
   */
  getBaskets(): Observable<Basket[]> {
    return this.http.get<Basket[]>(this.apiUrl);
  }

  /**
   * Retrieves a specific basket by its ID.
   * @param id - The unique identifier of the basket.
   * @returns Observable of the requested basket.
   */
  getBasket(id: number): Observable<Basket> {
    return this.http.get<Basket>(`${this.apiUrl}/${id}`);
  }

  /**
   * Searches for baskets that match a given name.
   * @param name - The name or partial name of the basket to search for.
   * @returns Observable of an array of baskets that match the query.
   */
  searchBaskets(name: string): Observable<Basket[]> {
    return this.http.get<Basket[]>(`${this.apiUrl}/?username=${name}`);
  }

  /**
   * Creates a new basket.
   * @param basket - The Basket object 
   * @returns Observable of the newly created Basket.
   */
  createBasket(basket: Basket): Observable<Basket> {
    return this.http.post<Basket>(this.apiUrl, basket);
  }

  /**
   * Updates an existing basket.
   * @param basket - The Basket object containing updated details.
   * @returns Observable of the updated Basket.
   */
  updateBasket(basket: Basket): Observable<Basket> {
    return this.http.put<Basket>(this.apiUrl, basket);
  }

  
  /**
   * Deletes a basket by its ID.
   * @param id - The unique identifier of the basket to delete.
   * @returns Observable of void upon successful deletion.
   */
  deleteBasket(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
