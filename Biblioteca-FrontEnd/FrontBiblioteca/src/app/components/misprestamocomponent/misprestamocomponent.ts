import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

interface Prestamo {
  id?: number;
  libroTitulo: string;
  fechaPrestamo: string; // ISO
  fechaLimite: string; // ISO
  estado: 'Pendiente' | 'Activo' | 'Vencido' | 'Devuelto' | 'Rechazado' | string;
  sancion?: string | null;
}

@Component({
  selector: 'app-misprestamocomponent',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './misprestamocomponent.html',
  styleUrls: ['./misprestamocomponent.css'],
})
export class Misprestamocomponent implements OnInit {
  prestamos: Prestamo[] = [];
  activos: Prestamo[] = [];
  vencidos: Prestamo[] = [];
  historial: Prestamo[] = [];
  Math = Math;

  loading = false;
  private baseUrl = environment.baseUrl || '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadMisPrestamos();
  }

  loadMisPrestamos(): void {
    this.loading = true;
    const url = `${this.baseUrl}/prestamos/mis`;
    this.http.get<Prestamo[]>(url).subscribe({
      next: (data) => {
        this.prestamos = data || [];
        this.categorizar();
        this.loading = false;
      },
      error: () => {
        // fallback mock datos
        this.prestamos = [
          { id: 1, libroTitulo: 'Introducción a Angular', fechaPrestamo: '2026-06-01', fechaLimite: '2026-06-15', estado: 'Activo' },
          { id: 2, libroTitulo: 'Algoritmos', fechaPrestamo: '2026-05-01', fechaLimite: '2026-05-15', estado: 'Devuelto' },
          { id: 3, libroTitulo: 'Arquitectura de Software', fechaPrestamo: '2026-05-10', fechaLimite: '2026-05-20', estado: 'Vencido', sancion: 'Multa pendiente' },
        ];
        this.categorizar();
        this.loading = false;
      },
    });
  }

  categorizar(): void {
    const now = new Date();
    this.activos = [];
    this.vencidos = [];
    this.historial = [];

    for (const p of this.prestamos) {
      const limite = new Date(p.fechaLimite);
      if (p.estado === 'Devuelto' || p.estado === 'Rechazado') {
        this.historial.push(p);
      } else if (limite < now) {
        p.estado = 'Vencido';
        this.vencidos.push(p);
      } else {
        this.activos.push(p);
      }
    }
  }

  diasRestantes(p: Prestamo): number {
    const hoy = new Date();
    const limite = new Date(p.fechaLimite);
    const diff = Math.ceil((limite.getTime() - hoy.getTime()) / (1000 * 60 * 60 * 24));
    return diff;
  }

  verSancion(p: Prestamo): void {
    if (p.sancion) alert(`Sanción: ${p.sancion}`);
    else alert('No hay sanción asociada.');
  }
}
