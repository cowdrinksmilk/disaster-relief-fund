/**
 * Service for handling API interactions related to user's individual disasters
 * Provides methods to retrieve, search, create, update, and delete disasters.
 *
 * Author[s]: Emma Wheeler, Mary Almazan
 * 
 * Major credit to Mary, most of this is just refactoring from her need.service.ts
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Interface representing a Disaster.
 */
export interface Disaster {
  id?: number;        // Unique identifier for the disaster (optional)
  name: string;       // Name of the disaster
  description: string; // description of the disaster
  items: number[];       // list of need id's 
}

@Injectable({
  providedIn: 'root'
})
export class DisastersService {
  private apiUrl = 'http://localhost:8080/disasters'; //may need to be changed. i don't really know this stuff too well

  constructor(private http: HttpClient) {}

  /**
   * Retrieves all disaster.
   * @returns Observable of an array of disasters.
   */
  getDisasters(): Observable<Disaster[]> {
    return this.http.get<Disaster[]>(this.apiUrl);
  }

  /**
   * Retrieves a specific disaster by its ID.
   * @param id - The unique identifier of the disaster.
   * @returns Observable of the requested disaster.
   */
  getDisaster(id: number): Observable<Disaster> {
    return this.http.get<Disaster>(`${this.apiUrl}/${id}`);
  }

  /**
   * Searches for disasters that match a given name.
   * @param name - The name or partial name of the disaster to search for.
   * @returns Observable of an array of disasters that match the query.
   */
  searchDisasters(name: string): Observable<Disaster[]> {
    return this.http.get<Disaster[]>(`${this.apiUrl}/?name=${name}`);
  }

  /**
   * Creates a new disaster.
   * @param disaster - The Disaster object 
   * @returns Observable of the newly created Disaster.
   */
  createDisaster(disaster: Disaster): Observable<Disaster> {
    return this.http.post<Disaster>(this.apiUrl, disaster);
  }

  /**
   * Updates an existing disaster.
   * @param disaster - The Disaster object containing updated details.
   * @returns Observable of the updated Disaster.
   */
  updateDisaster(disaster: Disaster): Observable<Disaster> {
    return this.http.put<Disaster>(this.apiUrl, disaster);
  }

  
  /**
   * Deletes a disaster by its ID.
   * @param id - The unique identifier of the disaster to delete.
   * @returns Observable of void upon successful deletion.
   */
  deleteDisaster(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}