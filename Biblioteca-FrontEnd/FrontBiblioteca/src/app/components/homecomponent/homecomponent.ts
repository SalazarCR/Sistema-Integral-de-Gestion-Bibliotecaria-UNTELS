import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

interface Stats {
  totalLibros: number;
  prestamosActivos: number;
  prestamosVencidos: number;
  estudiantes: number;
  sancionesActivas: number;
  solicitudesPendientes?: number;
}

@Component({
  selector: 'app-homecomponent',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './homecomponent.html',
  styleUrls: ['./homecomponent.css'],
})
export class Homecomponent implements OnInit {
  stats: Stats = {
    totalLibros: 0,
    prestamosActivos: 0,
    prestamosVencidos: 0,
    estudiantes: 0,
    sancionesActivas: 0,
    solicitudesPendientes: 0,
  };

  loading = false;
  private baseUrl = environment.baseUrl || '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.loading = true;
    const url = `${this.baseUrl}/dashboard/summary`;
    this.http.get<Stats>(url).subscribe({
      next: (data) => {
        if (data) this.stats = data;
        this.loading = false;
      },
      error: () => {
        // fallback a datos mock si no hay backend disponible
        this.stats = {
          totalLibros: 1240,
          prestamosActivos: 87,
          prestamosVencidos: 12,
          estudiantes: 520,
          sancionesActivas: 5,
          solicitudesPendientes: 4,
        };
        this.loading = false;
      },
    });
  }
}
