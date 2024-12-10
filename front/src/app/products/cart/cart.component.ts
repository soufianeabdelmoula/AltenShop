import { Component, OnInit } from '@angular/core';
import { CartService } from '../data-access/cart.service';
import { Product } from '../data-access/product.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  standalone: true,
  imports: [CommonModule],
})
export class CartComponent implements OnInit {
  private cartItemsSubject: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);  // Initialisation avec un tableau vide
  cartItems$: Observable<Product[]>;
  totalPrice: number = 0;  // Variable pour stocker le total

  constructor(private cartService: CartService) {
    // cartItems$ est un observable qui contient les éléments du panier
    this.cartItems$ = this.cartService.cartItems$;
  }

  ngOnInit() {
    this.loadCartItems();
    this.calculateTotal();
  }

  // Charger les produits du panier depuis localStorage
  loadCartItems() {
    const storedItems = localStorage.getItem('cartItems');
    const cartItems = storedItems ? JSON.parse(storedItems) : [];  // Si des items sont stockés, on les charge
    this.cartItemsSubject.next(cartItems);  // Mettez à jour le subject avec les produits récupérés
  }

  removeFromCart(productId: string): void {
    this.cartService.removeFromCart(productId);
    this.calculateTotal();  // Recalculer le total après suppression
  }

  // Calculer le total des produits dans le panier
  calculateTotal() {
    const items = this.cartItemsSubject.value;
    this.totalPrice = items.reduce((sum, item) => sum + item.price, 0);
  }
}
