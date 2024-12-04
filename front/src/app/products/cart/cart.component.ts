import { Component, OnInit } from '@angular/core';
import { CartService } from '../data-access/cart.service';
import { CommonModule } from '@angular/common';
import { Product } from '../data-access/product.model';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  standalone: true,  
  imports: [CommonModule] 
})
export class CartComponent implements OnInit {
  private cartItemsSubject: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);  // Initialisation avec un tableau vide
  cartItems$: Observable<Product[]>;

  constructor(private cartService: CartService) {
    // cartItems$ est un observable qui contient les éléments du panier
    this.cartItems$ = this.cartService.cartItems$;
  }

  ngOnInit() {
    this.loadCartItems();  // Charger les articles du panier au démarrage
  }

  // Charger les produits du panier depuis localStorage
  loadCartItems() {
    const storedItems = localStorage.getItem('cartItems');
    const cartItems = storedItems ? JSON.parse(storedItems) : [];  // Si des items sont stockés, on les charge
    this.cartItemsSubject.next(cartItems);  // Mettez à jour le subject avec les produits récupérés
  }

  removeFromCart(productId: string): void {
    this.cartService.removeFromCart(productId);
  }

  // Obtenez le nombre de produits dans le panier
  getCartCount() {
    return this.cartItemsSubject.value.length;  // Utilisez cartItemsSubject pour obtenir le nombre
  }
}
