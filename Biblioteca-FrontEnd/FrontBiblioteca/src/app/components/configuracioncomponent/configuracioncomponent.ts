import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Configuracionservice } from '../../services/configuracionservice';

@Component({
  selector: 'app-configuracioncomponent',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './configuracioncomponent.html',
  styleUrls: ['./configuracioncomponent.css'],
})
export class Configuracioncomponent implements OnInit {
  form!: FormGroup;

  loading = false;
  saving = false;

  constructor(private fb: FormBuilder, private configSvc: Configuracionservice) {
    this.form = this.fb.group({
      diasMaximosPrestamo: [30, [Validators.required, Validators.min(1)]],
      limitePrestamos: [3, [Validators.required, Validators.min(0)]],
      montoMulta: [0, [Validators.required, Validators.min(0)]],
      diasSuspension: [0, [Validators.required, Validators.min(0)]],
      activarNotificaciones: [true],
      permitirSolicitudes: [true],
    });
  }

  ngOnInit(): void {
    this.loadConfiguracion();
  }

  loadConfiguracion(): void {
    this.loading = true;
    this.configSvc.getConfiguracion().subscribe({
      next: (cfg) => {
        if (cfg) {
          this.form.patchValue({
            diasMaximosPrestamo: cfg.diasMaximosPrestamo ?? this.form.value.diasMaximosPrestamo,
            limitePrestamos: cfg.limitePrestamos ?? this.form.value.limitePrestamos,
            montoMulta: cfg.montoMulta ?? this.form.value.montoMulta,
            diasSuspension: cfg.diasSuspension ?? this.form.value.diasSuspension,
            activarNotificaciones: cfg.activarNotificaciones ?? this.form.value.activarNotificaciones,
            permitirSolicitudes: cfg.permitirSolicitudes ?? this.form.value.permitirSolicitudes,
          });
        }
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.saving = true;
    this.configSvc.updateConfiguracion(this.form.value).subscribe({
      next: () => {
        this.saving = false;
        alert('Configuración guardada correctamente.');
      },
      error: (err) => {
        this.saving = false;
        console.error(err);
        alert('Error al guardar la configuración.');
      },
    });
  }

  cancel(): void {
    this.loadConfiguracion();
  }
}
