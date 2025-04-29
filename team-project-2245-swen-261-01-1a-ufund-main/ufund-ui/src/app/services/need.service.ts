/**
 * Service for handling API interactions related to "Needs" in the cupboard.
 * Provides methods to retrieve, search, create, update, and delete needs.
 *
 * 
 * Author[s]: Mary Almazan, Emma Wheeler [to a significantly smaller degree]
 * 
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Interface representing a Need.
 */
export interface Need {
  id?: number;        // Unique identifier for the need (optional)
  name: string;       // Name of the need
  description: string;// Description of the need
  quantity: number;   // Quantity required
  cost: number;       // Estimated cost
}

@Injectable({
  providedIn: 'root'
})
export class NeedService {
  private apiUrl = 'http://localhost:8080/needs';

  constructor(private http: HttpClient) {}

  /**
   * Retrieves all needs from the cupboard.
   * @returns Observable of an array of Needs.
   */
  getNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.apiUrl);
  }

  /**
   * Retrieves a specific need by its ID.
   * @param id - The unique identifier of the need.
   * @returns Observable of the requested Need.
   */
  getNeed(id: number): Observable<Need> {
    return this.http.get<Need>(`${this.apiUrl}/${id}`);
  }

  //DOESN'T WORK, no compatible search feature in controller. will fix later. 
  /**
   * Searches for needs that match a given name.
   * @param name - The name or partial name of the need to search for.
   * @returns Observable of an array of Needs that match the query.
   
  searchNeeds(name: string): Observable<Need[]> {
    return this.http.get<Need[]>(`${this.apiUrl}/?name=${name}`);
  }
    */

  /**
   * Searches for needs by their description.
   * @param description - The description or partial description of the need.
   * @returns Observable of an array of Needs that match the query.
   */
  searchNeedsByDescription(description: string): Observable<Need[]> {
    return this.http.get<Need[]>(`${this.apiUrl}/?desc=${description}`);
  }

  /**
   * Creates a new need in the cupboard.
   * @param need - The Need object containing details like name, description, quantity, and cost.
   * @returns Observable of the newly created Need.
   */
  createNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(this.apiUrl, need);
  }

  /**
   * Updates an existing need.
   * @param need - The Need object containing updated details.
   * @returns Observable of the updated Need.
   */
  updateNeed(need: Need): Observable<Need> {
    return this.http.put<Need>(this.apiUrl, need);
  }

  /**
   * Deletes a need from the cupboard by its ID.
   * @param id - The unique identifier of the need to delete.
   * @returns Observable of void upon successful deletion.
   */
  deleteNeed(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
