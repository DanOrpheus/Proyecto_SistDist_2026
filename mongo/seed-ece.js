db.expedientes.deleteMany({});

db.expedientes.insertMany([
  {
    curpPaciente: "OOVI970830HSRCGV01",
    nombreCompleto: "Ivan Alejandro Ochoa Vega",
    edad: 28,
    fechaNacimiento: "1997-08-30",
    sexo: "Masculino",
    domicilio: "Hermosillo, Sonora",
    tipoSangre: "O+",
    alergias: ["Ninguna"],
    notasClinicas: "Expediente clinico activo para consulta medica autorizada.",
    tutor: null,
    biometriaPaciente: {
      tipo: "Huella dactilar",
      estado: "Validada",
      fechaValidacion: "2026-05-18"
    },
    medicoAutorizado: {
      nombreCompleto: "Dra. Sofia Martinez",
      cedulaProfesional: "CED-928374",
      especialidad: "Medicina general",
      institucion: "Hospital General de Hermosillo",
      accesoDesde: "2026-05-18T08:00:00",
      accesoHasta: "2026-05-18T20:00:00",
      motivoAcceso: "Cita medica programada",
      estadoAcceso: "Pendiente",
      fechaSolicitud: "2026-05-18T07:50:00",
      fechaAutorizacion: null
    },
    consultas: [
      {
        fecha: "2026-05-18",
        medico: "Dra. Sofia Martinez",
        cedulaProfesional: "CED-928374",
        diagnostico: "Revision general",
        tratamiento: "Control preventivo y seguimiento anual",
        notas: "Paciente estable, sin datos de alarma."
      },
      {
        fecha: "2025-11-04",
        medico: "Dr. Luis Hernandez",
        cedulaProfesional: "CED-451203",
        diagnostico: "Infeccion respiratoria leve",
        tratamiento: "Reposo e hidratacion",
        notas: "Evolucion favorable."
      }
    ],
    imagenes: [
      {
        tipo: "Radiografia",
        nombre: "Radiografia de torax",
        fecha: "2025-11-04",
        descripcion: "Imagen de apoyo para revision respiratoria.",
        urlDemo: "radiografia-torax-demo.jpg"
      }
    ],
    documentosPdf: [
      {
        tipo: "Laboratorio",
        nombre: "Biometria hematica",
        fecha: "2026-05-15",
        descripcion: "Resultados de laboratorio previos a la cita.",
        urlDemo: "biometria-hematica-demo.pdf"
      }
    ],
    historialAccesos: [
      {
        fechaHora: "2026-05-18T07:55:00",
        actor: "Ivan Alejandro Ochoa Vega",
        rol: "Paciente",
        accion: "Recibio solicitud de acceso",
        metodoAutorizacion: "Cedula profesional",
        resultado: "Pendiente"
      },
      {
        fechaHora: "2026-05-18T08:10:00",
        actor: "Dra. Sofia Martinez",
        rol: "Medico",
        accion: "Consulto expediente",
        metodoAutorizacion: "Token de sesion + cedula profesional",
        resultado: "Permitido"
      }
    ]
  },
  {
    curpPaciente: "RULM850901MSONPR09",
    nombreCompleto: "Mariana Ruiz Lopez",
    edad: 40,
    fechaNacimiento: "1985-09-01",
    sexo: "Femenino",
    domicilio: "Guaymas, Sonora",
    tipoSangre: "O+",
    alergias: ["Ninguna"],
    notasClinicas: "Expediente personal activo. Responsable legal de una menor bajo tutela.",
    tutor: null,
    biometriaPaciente: {
      tipo: "Huella dactilar",
      estado: "Validada",
      fechaValidacion: "2026-05-17"
    },
    medicoAutorizado: {
      nombreCompleto: "Dra. Sofia Martinez",
      cedulaProfesional: "CED-928374",
      especialidad: "Medicina general",
      institucion: "Hospital General de Hermosillo",
      accesoDesde: "2026-05-18T10:00:00",
      accesoHasta: "2026-05-18T18:00:00",
      motivoAcceso: "Consulta medica personal",
      estadoAcceso: "Sin solicitud",
      fechaSolicitud: null,
      fechaAutorizacion: null
    },
    consultas: [
      {
        fecha: "2026-04-22",
        medico: "Dra. Sofia Martinez",
        cedulaProfesional: "CED-928374",
        diagnostico: "Revision preventiva",
        tratamiento: "Seguimiento anual",
        notas: "Sin datos de alarma. Biometria validada."
      }
    ],
    imagenes: [],
    documentosPdf: [
      {
        tipo: "Laboratorio",
        nombre: "Quimica sanguinea",
        fecha: "2026-04-20",
        descripcion: "Resultados de laboratorio preventivo.",
        urlDemo: "quimica-sanguinea-demo.pdf"
      }
    ],
    historialAccesos: [
      {
        fechaHora: "2026-05-17T18:05:00",
        actor: "Mariana Ruiz Lopez",
        rol: "Paciente",
        accion: "Actualizo perfil de tutor",
        metodoAutorizacion: "Huella dactilar",
        resultado: "Registrado"
      }
    ]
  },
  {
    curpPaciente: "NINA120304MSONRR05",
    nombreCompleto: "Valeria Ramirez Ruiz",
    edad: 14,
    fechaNacimiento: "2012-03-04",
    sexo: "Femenino",
    domicilio: "Guaymas, Sonora",
    tipoSangre: "A+",
    alergias: ["Penicilina"],
    notasClinicas: "Paciente menor de edad. El acceso requiere autorizacion del tutor.",
    tutor: {
      nombreCompleto: "Mariana Ruiz Lopez",
      parentesco: "Madre",
      curp: "RULM850901MSONPR09",
      telefono: "662-555-0190"
    },
    biometriaPaciente: {
      tipo: "Autorizacion de tutor por huella",
      estado: "Validada",
      fechaValidacion: "2026-05-17"
    },
    medicoAutorizado: {
      nombreCompleto: "Dr. Andres Lopez",
      cedulaProfesional: "CED-774410",
      especialidad: "Pediatria",
      institucion: "Clinica Infantil del Norte",
      accesoDesde: "2026-05-19T09:00:00",
      accesoHasta: "2026-05-19T12:00:00",
      motivoAcceso: "Consulta pediatrica",
      estadoAcceso: "Autorizado",
      fechaSolicitud: "2026-05-17T18:10:00",
      fechaAutorizacion: "2026-05-17T18:20:00"
    },
    consultas: [
      {
        fecha: "2026-05-10",
        medico: "Dr. Andres Lopez",
        cedulaProfesional: "CED-774410",
        diagnostico: "Rinitis alergica",
        tratamiento: "Antihistaminico y evitar alergenos",
        notas: "Se registra alergia a penicilina."
      }
    ],
    imagenes: [],
    documentosPdf: [
      {
        tipo: "Laboratorio",
        nombre: "Prueba de alergias",
        fecha: "2026-05-09",
        descripcion: "Documento PDF con resultados de alergias.",
        urlDemo: "prueba-alergias-demo.pdf"
      }
    ],
    historialAccesos: [
      {
        fechaHora: "2026-05-17T18:20:00",
        actor: "Mariana Ruiz Lopez",
        rol: "Tutor",
        accion: "Autorizo acceso de menor",
        metodoAutorizacion: "Huella dactilar",
        resultado: "Autorizado"
      }
    ]
  },
  {
    curpPaciente: "LABP900808HDFRRL02",
    nombreCompleto: "Pedro Lara Beltran",
    edad: 35,
    fechaNacimiento: "1990-08-08",
    sexo: "Masculino",
    domicilio: "Ciudad de Mexico",
    tipoSangre: "B-",
    alergias: ["Mariscos", "Ibuprofeno"],
    notasClinicas: "Expediente con estudios de laboratorio e imagenologia.",
    tutor: null,
    biometriaPaciente: {
      tipo: "Reconocimiento facial",
      estado: "Validada",
      fechaValidacion: "2026-05-16"
    },
    medicoAutorizado: {
      nombreCompleto: "Dra. Elena Torres",
      cedulaProfesional: "CED-118902",
      especialidad: "Cardiologia",
      institucion: "Hospital Privado Reforma",
      accesoDesde: "2026-05-20T10:00:00",
      accesoHasta: "2026-05-20T14:00:00",
      motivoAcceso: "Valoracion cardiologica",
      estadoAcceso: "Autorizado",
      fechaSolicitud: "2026-05-16T11:20:00",
      fechaAutorizacion: "2026-05-16T11:30:00"
    },
    consultas: [
      {
        fecha: "2026-05-01",
        medico: "Dra. Elena Torres",
        cedulaProfesional: "CED-118902",
        diagnostico: "Hipertension en observacion",
        tratamiento: "Monitoreo de presion arterial",
        notas: "Se solicita electrocardiograma y laboratorios."
      }
    ],
    imagenes: [
      {
        tipo: "Imagenologia",
        nombre: "Electrocardiograma",
        fecha: "2026-05-03",
        descripcion: "Imagen del trazo cardiaco para valoracion.",
        urlDemo: "electrocardiograma-demo.png"
      }
    ],
    documentosPdf: [
      {
        tipo: "Laboratorio",
        nombre: "Perfil metabolico",
        fecha: "2026-05-02",
        descripcion: "Resultados de glucosa, colesterol y trigliceridos.",
        urlDemo: "perfil-metabolico-demo.pdf"
      }
    ],
    historialAccesos: [
      {
        fechaHora: "2026-05-16T11:30:00",
        actor: "Pedro Lara Beltran",
        rol: "Paciente",
        accion: "Autorizo acceso remoto",
        metodoAutorizacion: "Reconocimiento facial",
        resultado: "Autorizado"
      }
    ]
  }
]);

print("Base ece_nacional lista.");
print("Coleccion expedientes: " + db.expedientes.countDocuments() + " documentos.");
