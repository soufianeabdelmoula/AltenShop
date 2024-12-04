import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
  standalone: true,  // Assurez-vous que votre composant est déclaré comme autonome
  imports: [CommonModule, ReactiveFormsModule,ToastModule],
  providers: [MessageService]
})
export class ContactComponent implements OnInit {
  contactForm!: FormGroup;
  successMessage: string = '';  // Ajouter une variable pour le message de succès
  showSuccessMessage: boolean = false;


  constructor(private fb: FormBuilder,private messageService: MessageService) {}

  ngOnInit(): void {
    // Initialiser le formulaire avec des validations
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]]
    });
  }

  onSubmit(): void {
    if (this.contactForm.valid) {
      const formData = this.contactForm.value;
      console.log('Form Data:', formData);

      // Affichez le toast avec le message de succès
      this.messageService.add({ severity: 'success', summary: 'Demande envoyée', detail: 'Votre demande de contact a été envoyée avec succès.' });

      // Réinitialiser le formulaire après l'envoi
      this.contactForm.reset();
    }
  }
}
  

