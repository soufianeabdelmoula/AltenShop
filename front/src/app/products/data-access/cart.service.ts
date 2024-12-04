import { Injectable } from '@angular/core';
import { Product } from './product.model';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  // Initialisez cartItemsSubject avec un tableau vide pour éviter l'erreur
  private cartItemsSubject: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]); 
  cartItems$: Observable<Product[]> = this.cartItemsSubject.asObservable();

  constructor() {}

  // Méthode pour ajouter un produit au panier
  addToCart(product: Product): void {
    if (product) {
      const currentItems = this.cartItemsSubject.value;
      this.cartItemsSubject.next([...currentItems, product]);
    }
  }

  // Méthode pour retirer un produit du panier
  removeFromCart(productId: string): void {
    // Filtrer les produits en fonction de l'id
    const updatedItems = this.cartItemsSubject.value.filter((item, index) => {
      // Vérifier si l'id ne correspond pas et ne supprimer qu'une seule occurrence du produit
      return item.id.toString() !== productId || index !== this.cartItemsSubject.value.findIndex(p => p.id.toString() === productId);
    });
  
    // Mettre à jour le panier avec les produits restants
    this.cartItemsSubject.next(updatedItems);
  }

  // Retourne le nombre d'éléments dans le panier
  getCartCount(): number {
    return this.cartItemsSubject.value.length;
  }
}
