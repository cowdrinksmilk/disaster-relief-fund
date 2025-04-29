/**
 * UFund Disaster Relief - Angular Component
 * This component handles all user interactions with the Need system,
 * allowing users to retrieve, search, add, update, and delete needs
 * in the cupboard.
 * It also allows users to put needs in/take needs out of their basket, 
 * as well as purchase them
 *
 * 
 * Author[s]: Mary Almazan, Demitri Clark, Emma Wheeler
 */

import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NeedService, Need } from './services/need.service';
import { BasketsService, Basket } from './services/baskets.service';
import { DisastersService, Disaster } from './services/disasters.service';

export interface receiptInfo {
  item: String;
  count: number;
  cost: number;
}


// declare function updateDisplayQuantity(): any;
  
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ufund-ui';


  /** Stores all retrieved needs */
  needs: Need[] = [];

  /** Stores all retrieved basket */
  baskets: Basket[] = [];

  /** Stores all retrieved basket */
  disasters: Disaster[] = [];

  /** Current user's basket */
  userBasket: Basket = { id: 0, username: '', items: [], counts: []}; 

  /** Holds data for a new need */
  newNeed: Need = { name: '', description: '', quantity: 0, cost: 0 };

  /** Holds data for a new basket */
  newBasket: Basket = {id:0, username: '', items: [], counts: []};

  /** Holds data for a new disaster */
  newDisaster: Disaster = {id:0, name: '', description:'', items: []};

  buyCounts: number[] = [];


  /** Stores the ID input for retrieving a specific need */
  searchId: number | null = null;

  /** Stores the search query for name-based filtering */
  searchQuery: string = '';

  /** Stores the search query for description-based filtering */
  searchQueryDesc: string = '';

   //info for receipt
   total: number = 0;
  
   receipt: receiptInfo[] = [];
 
   date: Date = new Date();
 
   isEmpty: number[] = [];

  disastersEmpty: number[] = [];
  /**
   * Injects the NeedService for API interactions.
   * @param needService - The service handling HTTP requests.
   * @param basketService - same, for baskets
   * @param disasterService - same, for disasters
   */
  constructor(private needService: NeedService, private basketsService: BasketsService, private disastersService: DisastersService) {}

  /**
   * Initializes the component by loading all needs.
   */
  ngOnInit(): void {
    this.loadNeeds();
    this.loadBaskets();
    this.loadDisasters()
    //updateDisplayQuantity();
  }

  /**
   * Fetches all needs from the API and updates the list.
   */
  loadNeeds(): void {
    this.needService.getNeeds().subscribe((data) => (this.needs = data));
  }

  loadSpecificNeed(id: number): void {
    this.needService.getNeed(id).subscribe((data) => (this.needs = this.needs.concat(data)));
  }

  /**
   * Fetches all baskets from the API and updates the list.
   */
  loadBaskets(): void {
    this.basketsService.getBaskets().subscribe((data) => (this.baskets = data));
  }


  /**
   * Fetches all disasters from the API and updates the list.
   */
  loadDisasters(): void {
    this.disastersService.getDisasters().subscribe((data) => (this.disasters = data));
    if(this.disasters.length >= 1){
      if( this.disastersEmpty.length == 0) {
        this.disastersEmpty.push(1);
      }
    } else {
      this.disastersEmpty = [];
    }
  }
  /**
   * Fetches the basket of this specific user
   */
  loadUserBasket(): void {
    if(this.userBasket.username !== 'Admin'){
      this.searchBasketsByName();
      var testBasket = this.baskets.find((basket) => basket.username == this.userBasket.username)
      if(testBasket){
        this.userBasket = testBasket;
        this.updateBasket(this.userBasket);
        if(this.userBasket.items.length != 0){
          this.isEmpty.push(1);
        }
      } else {
        this.createBasket();
      }
    }
    this.loadDisasters();
  }

  getBuyCountIndex(need: Need): number{
    var index = this.needs.findIndex((searchNeed) => searchNeed == need);
    if(index !== undefined){
      return index;
    }
    else{
      return 0;
    }
  }

  getBasketIndex(needId: number): number{
    var index = this.userBasket.items.findIndex((searchId) => searchId == needId);
    if(index !== undefined){
      return index;
    }
    else{
      return 0;
    }
  }

  /**
   * Fetches a need from the list of needs by its id 
   * Used for basket display
   */
  getNeedByID(givenId: number): Need{
    var returnNeed = this.needs.find((need) => need.id == givenId);
    if(returnNeed){
      return returnNeed;
    }
    else{
      return this.newNeed;
    }
  }

  
  /**
   * Retrieves a specific need by its ID and updates the displayed list.
   * If the need is not found, an alert is shown.
   */
  retrieveNeed(): void {
    if (this.searchId !== null) {
      this.needService.getNeed(this.searchId).subscribe(
        (data) => (this.needs = [data]),
        (error) => alert("Need not found")
      );
    }
  }

   /**
   * Retrieves a specific need by its ID and updates the displayed list.
   * If the need is not found, an alert is shown.
   */
   retrieveDisaster(): void {
    if (this.searchId !== null) {
      this.disastersService.getDisaster(this.searchId).subscribe(
        (data) => (this.disasters = [data]),
        (error) => alert("Disaster not found")
      );
    }
  }

  

  /**
   * Searches for needs that match the provided name.
   * If the search field is empty, reloads all needs.
   * 
   * //doesn't work, see need.service.ts. -Emma
   * 
  */
  //searchNeeds(): void {
  //  if (this.searchQuery) {
  //    this.needService.searchNeeds(this.searchQuery).subscribe((data) => (this.needs = data));
  //  } else {
  //    this.loadNeeds();
  //  }
  //}
  

  /**
   * Searches for needs that match the provided description.
   * If the search field is empty, reloads all needs.
   */
  searchNeedsByDescription(): void {
    if (this.searchQueryDesc) {
      this.needService.searchNeedsByDescription(this.searchQueryDesc).subscribe((data) => (this.needs = data));
    } else {
      this.loadNeeds();
    }
  }

  /**
   * Searches for baskets that have a similar name.
   * If the search field is empty, reloads all needs.
   */
  searchBasketsByName(): void {
    if (this.userBasket.username) {
      this.basketsService.searchBaskets(this.userBasket.username).subscribe((data) => (this.baskets = data));
    } else {
      this.loadBaskets();
    }
  }

  searchNeedsByDisasterA(): void {
    if (this.searchQuery) {
      this.disastersService.searchDisasters(this.searchQuery).subscribe((data) => (this.disasters = data));
      if(this.disasters.length != 0){
        this.needs = [];
        for(let i of this.disasters[0].items){
          this.loadSpecificNeed(i);
        }
      }
      else{
        this.loadNeeds();
      }
    } else {
      this.loadNeeds();
    }
    this.loadDisasters();
  }

  searchNeedsByDisasterB(disaster: Disaster): void {
    if(disaster.items.length != 0){
      this.needs = [];
      for(let i of disaster.items){
        this.loadSpecificNeed(i);
      }
    }
    else{
      this.loadNeeds();
    }
    this.loadDisasters();
  }


  

  //search all disasters by name
  searchDisastersByName(): void {
    if (this.searchQueryDesc) {
      this.disastersService.searchDisasters(this.searchQueryDesc).subscribe((data) => (this.disasters = data));
    } else {
      this.loadDisasters();
    }
  }


  /**
   * Adds a new need to the cupboard.
   * After creation, resets the input fields and reloads the need list.
   */
  addNeed(): void {
    this.needService.createNeed(this.newNeed).subscribe(() => {
      this.loadNeeds();
      this.newNeed = { name: '', description: '', quantity: 0, cost: 0 }; // Reset form
    });
  }

  /**
   * Makes a new basket based on user information
   * SHOULD ONLY BE CALLED IF CURRENT USER HAS NONE IN SYSTEM
   * EVEN AN EMPTY ONE COUNTS
   */
  createBasket(): void {
    this.newBasket.username = this.userBasket.username;
    this.basketsService.createBasket(this.newBasket).subscribe(() => {
      this.loadBaskets();
      this.newBasket = { username: '', items: [], counts: []};
    });
  }

  //create a new disaster
  addDisaster(): void {
    this.disastersService.createDisaster(this.newDisaster).subscribe(() => {
      this.loadDisasters();
      this.newDisaster = { name: '', description: '', items: []};
    });
    if(this.disastersEmpty.length == 0){
      this.disastersEmpty.push(1);
    }
  }

  /**
   * Updates an existing need with modified details.
   * After updating, reloads the need list.
   * @param need - The updated need object.
   */
  updateNeed(need: Need): void {
    this.needService.updateNeed(need).subscribe(() => this.loadNeeds());
  }

  /**
   * Updates an existing basket with modified details.
   * After updating, reloads the basket list.
   * @param basket - The updated basket object.
   */
  updateBasket(basket: Basket): void {
    this.basketsService.updateBasket(basket).subscribe(() => this.loadBaskets());
  }


  /**
   * Updates an existing basket with modified details.
   * After updating, reloads the basket list.
   * @param basket - The updated basket object.
   */
  updateDisaster(disaster: Disaster): void {
    this.disastersService.updateDisaster(disaster).subscribe(() => this.loadDisasters());
  }
  /**
   * CURRENTLY UNUSED, WILL BE USED/FIXED WHEN CHANGING QUANTITY IN CART WORKS
   * @param need the need in the cart
   * @returns the count of a need in the cart based on the given need's id
   */
  getQuantityByNeed(need: Need): number | undefined{
    var index = this.userBasket.items.findIndex((searchNeed) => searchNeed == need.id)
    if(index !== undefined){
      var value = this.userBasket.counts.at(index);
      return this.userBasket.counts.find((count) => count == value);
    }
    else{
      return 0;
    }
  }


  /**
   * Deletes a need by its ID.
   * After deletion, reloads the need list.
   * @param id - The unique identifier of the need to delete.
   */
  deleteNeed(id?: number): void {
    if (id !== undefined) {
      this.needService.deleteNeed(id).subscribe(() => this.loadNeeds());
    }
  }

  /**
   * Deletes a basket by its ID.
   * After deletion, reloads the basket list.
   * @param id - The unique identifier of the basket to delete.
   */
  deleteBasket(id?: number): void {
    if (id !== undefined) {
      this.basketsService.deleteBasket(id).subscribe(() => this.loadBaskets());
    }
  }

  /**
   * Deletes a basket by its ID.
   * After deletion, reloads the basket list.
   * @param id - The unique identifier of the basket to delete.
   */
  deleteDisaster(id?: number): void {
    if (id !== undefined) {
      this.disastersService.deleteDisaster(id).subscribe(() => this.loadDisasters());
    }
    if(this.disasters.length == 0){
      this.disastersEmpty = [];
    }
  }


  /**
   * Adds an item['s id] to the basket.
   * Will probably be updated to feature a quantity increaser, until then just adds 1 each time.
   * @param need the need to be added
   */
  addItemToBasket(need: Need, quantity: number): void{
    if(need.id){
      var id = this.userBasket.items.find((searchId) => searchId == need.id);
      if(id){
        var index = this.userBasket.items.findIndex((searchId) => searchId == id);
        this.userBasket.counts[index] += quantity;
      }
      else{
        this.userBasket.items.push(need.id);
        if(quantity){
          this.userBasket.counts.push(quantity);
        }
        else{
          this.userBasket.counts.push(1);
        }
      }
      var index = this.getBuyCountIndex(need);
      this.buyCounts[index] = 1;
      this.updateBasket(this.userBasket);
      if(this.isEmpty.length == 0){
        this.isEmpty.push(1);
      }
    }
    
  }

  addItemToDisaster(need: Need, disaster: Disaster): void{
    if(need.id){
      var id = disaster.items.find((searchId) => searchId == need.id);
      if(!id){
        disaster.items.push(need.id);
      }
      this.updateDisaster(disaster);
    }
    
  }

  /**
   * Removes an item from the cart
   * @param need the need to remove
   */
  removeItemFromBasket(need: Need): void{
    var index = this.userBasket.items.findIndex((removeNeed) => removeNeed == need.id);
    this.userBasket.counts.splice(index, 1);
    this.userBasket.items.splice(index, 1);
    this.updateBasket(this.userBasket);
    if(this.userBasket.items.length == 0){
      this.isEmpty = [];
    }
  }

  removeNeedFromDisaster(need: Need, disaster: Disaster): void{
    var index = disaster.items.findIndex((removeNeed) => removeNeed == need.id);
    disaster.items.splice(index, 1);
    this.updateDisaster(disaster);
  }

  /**
   * "Purchases a need" by removing it from cart, decreasing its counter, 
   * and deleting it if count hits 0. 
   * @param need the need to purchase.
   */ 
  purchaseNeed(need: Need): void{
    var id = this.userBasket.items.find((searchId) => searchId == need.id);
    if(id){
      var index = this.userBasket.items.findIndex((searchId) => searchId == need.id);
      var quantity = this.userBasket.counts[index];
      if(need.quantity > quantity){
        need.quantity = need.quantity - quantity;
        this.total += need.cost * quantity;
        this.receipt.push({item: need.name, count: quantity, cost: (quantity * need.cost)}); 
        this.needService.updateNeed(need).subscribe(() => this.loadNeeds());
        this.removeItemFromBasket(need);
      } else{
        this.total += need.cost * need.quantity;
        this.receipt.push({item: need.name, count: need.quantity, cost: (need.quantity * need.cost)}); 
        this.removeItemFromBasket(need);
        this.deleteNeed(need.id);
      }
    } else {
      this.removeItemFromBasket(need);
    }
  }

  purchaseAllNeeds(): void{
    while(this.userBasket.items[0]){
      this.purchaseNeed(this.getNeedByID(this.userBasket.items[0]));
    }
    this.date = new Date();
  }

  clearReceipt(): void{
    this.receipt = [];
    this.total = 0;
  }
}