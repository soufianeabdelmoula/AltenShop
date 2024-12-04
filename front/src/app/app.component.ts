import {
  Component,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartService } from "./products/data-access/cart.service";
import { CommonModule } from "@angular/common";
import { MenuModule } from 'primeng/menu';


@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule,CommonModule, SplitterModule, ToolbarModule, PanelMenuComponent,MenuModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  constructor(public cartService: CartService) {}


  getCartCount(): number {
    return this.cartService.getCartCount();
  }
}

