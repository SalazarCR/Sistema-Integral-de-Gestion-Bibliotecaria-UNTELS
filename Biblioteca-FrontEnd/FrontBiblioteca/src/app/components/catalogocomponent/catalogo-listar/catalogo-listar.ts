import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

interface Libro {
  id?: number;
  titulo: string;
  autor: string;
  isbn?: string;
  categoria?: string;
  stock: number;
  disponible: boolean;
}

@Component({
  selector: 'app-catalogo-listar',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './catalogo-listar.html',
  styleUrls: ['./catalogo-listar.css'],
})
export class CatalogoListar implements OnInit {
  libros: Libro[] = [];
  resultado: Libro[] = [];

  query = '';
  field: 'titulo' | 'autor' | 'isbn' = 'titulo';
  onlyAvailable = false;

  loading = false;

  private baseUrl = environment.baseUrl || '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadLibros();
  }

  loadLibros(): void {
    this.loading = true;
    const url = `${this.baseUrl}/libros`;
    this.http.get<Libro[]>(url).subscribe({
      next: (data) => {
        this.libros = data || [];
        this.applyFilters();
        this.loading = false;
      },
      error: () => {
        // Fallback: datos mock si el backend no está disponible
        this.libros = [
          { id: 1, titulo: 'Introducción a Angular', autor: 'Autor A', isbn: '1111', categoria: 'Programación', stock: 3, disponible: true },
          { id: 2, titulo: 'Arquitectura de Software', autor: 'Autor B', isbn: '2222', categoria: 'Tecnología', stock: 0, disponible: false },
          { id: 3, titulo: 'Algoritmos', autor: 'Autor C', isbn: '3333', categoria: 'Ciencias', stock: 2, disponible: true },
        ];
        this.applyFilters();
        this.loading = false;
      },
    });
  }

  applyFilters(): void {
    const q = this.query.trim().toLowerCase();
    this.resultado = this.libros.filter((l) => {
      if (this.onlyAvailable && !l.disponible) return false;
      if (!q) return true;
      const value = (this.field === 'titulo' ? l.titulo : this.field === 'autor' ? l.autor : l.isbn || '').toLowerCase();
      return value.includes(q);
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  toggleAvailability(): void {
    this.onlyAvailable = !this.onlyAvailable;
    this.applyFilters();
  }

  solicitarPrestamo(libro: Libro): void {
    if (!libro.disponible || libro.stock <= 0) {
      alert('El libro no está disponible para préstamo.');
      return;
    }

    // Intentamos solicitar préstamo mediante endpoint estándar.
    const url = `${this.baseUrl}/prestamos/solicitar`;
    this.http.post(url, { libroId: libro.id }).subscribe({
      next: () => alert('Solicitud de préstamo enviada.'),
      error: () => alert('No se pudo enviar la solicitud. Intente más tarde.'),
    });
  }
}
