import React from 'react';

const svg = {
    search : {
        path: "M451.248,354.559c81.001-81.002,81-212.805,0-293.807c-81.002-81.002-212.802-81.002-293.803,0c-68.72,68.72-79.127,170.923-31.251,250.733c0,0,3.439,5.77-1.206,10.412C98.491,348.392,18.994,427.89,18.994,427.89c-21.097,21.096-26.12,50.595-7.411,69.307l3.221,3.219c18.709,18.712,48.21,13.691,69.305-7.406c0,0,79.329-79.33,105.771-105.771c4.867-4.868,10.635-1.429,10.635-1.429C280.325,433.685,382.528,423.278,451.248,354.559z M195.799,316.202c-59.853-59.853-59.851-157.238,0.001-217.091c59.853-59.851,157.238-59.853,217.09,0c59.853,59.851,59.853,157.238,0,217.091C353.038,376.052,255.654,376.052,195.799,316.202z M209.059,195.326c-2.786,0-5.618-0.548-8.348-1.701c-10.904-4.615-16.005-17.196-11.39-28.102c29.006-68.546,108.369-100.714,176.914-71.708c10.904,4.615,16.006,17.196,11.391,28.102c-4.616,10.906-17.194,16.004-28.104,11.391c-46.767-19.789-100.919,2.159-120.707,48.926C225.353,190.413,217.413,195.326,209.059,195.326z",
        view: "0 0 512 512"
    },
    login : {
        path: "M131.5,472H60.693c-8.538,0-13.689-4.765-15.999-7.606c-3.988-4.905-5.533-11.289-4.236-17.519c20.769-99.761,108.809-172.616,210.445-174.98c1.693,0.063,3.39,0.105,5.097,0.105c1.723,0,3.434-0.043,5.143-0.107c24.853,0.567,49.129,5.24,72.235,13.918c10.34,3.885,21.871-1.353,25.754-11.693c3.883-10.34-1.352-21.871-11.692-25.754c-3.312-1.244-6.646-2.408-9.995-3.512C370.545,220.021,392,180.469,392,136C392,61.01,330.991,0,256,0S120,61.01,120,136c0,44.509,21.492,84.092,54.643,108.918c-30.371,9.998-58.871,25.546-83.813,46.063c-45.732,37.617-77.529,90.086-89.532,147.742c-3.762,18.066,0.744,36.622,12.363,50.908C25.221,503.847,42.364,512,60.693,512H131.5c11.046,0,20-8.954,20-20S142.546,472,131.5,472z M160,136c0-52.935,43.065-96,96-96c52.935,0,96,43.065,96,96c0,51.367-40.554,93.438-91.326,95.885c-1.557-0.028-3.113-0.052-4.674-0.052c-1.564,0-3.127,0.023-4.689,0.051C200.546,229.43,160,187.362,160,136z M496.689,344.607c-8.562-19.15-27.846-31.559-49.177-31.607h-62.372c-0.045,0-0.087,0-0.133,0c-22.5,0-42.13,13.26-50.029,33.807c-1.051,2.734-2.336,6.178-3.677,10.193H200.356c-5.407,0-10.583,2.189-14.35,6.068l-34.356,35.388c-7.567,7.794-7.529,20.203,0.085,27.95l35,35.611c3.76,3.826,8.9,5.981,14.264,5.981h65c11.046,0,20-8.954,20-20s-8.954-20-20-20h-56.614l-15.428-15.698L208.814,397h137.491c9.214,0,17.235-6.295,19.427-15.244c1.617-6.607,3.647-12.959,6.584-20.596c1.936-5.036,6.798-8.16,12.74-8.16c0.014,0,0.026,0,0.039,0h62.371c5.656,0.014,10.524,3.053,12.705,7.932c5.369,12.012,11.78,30.608,11.828,50.986c0.048,20.529-6.356,39.551-11.739,51.894c-2.17,4.979-7.079,8.188-12.56,8.188c-0.011,0-0.022,0-0.033,0h-63.125c-5.533-0.013-10.716-3.573-12.896-8.858c-2.34-5.671-4.366-12.146-6.197-19.797c-2.571-10.742-13.367-17.365-24.105-14.796c-10.743,2.571-17.367,13.364-14.796,24.106c2.321,9.699,4.979,18.118,8.121,25.737c8.399,20.364,27.939,33.556,49.827,33.606h63.125c0.043,0,0.083,0,0.126,0c21.351-0.001,40.646-12.63,49.18-32.201c6.912-15.851,15.138-40.511,15.072-67.975C511.935,384.434,503.638,360.152,496.689,344.607z M431,392c11.046,0,20,8.954,20,20s-8.954,20-20,20s-20-8.954-20-20S419.954,392,431,392z",
        view: "0 0 512 512"
    },
    logout : {
        path: "M510.371,226.513c-1.088-2.603-2.646-4.971-4.629-6.955l-63.979-63.979c-8.341-8.32-21.823-8.32-30.165,0c-8.341,8.341-8.341,21.845,0,30.165l27.584,27.584H320.013c-11.797,0-21.333,9.557-21.333,21.333c0,11.776,9.536,21.333,21.333,21.333h119.168l-27.584,27.584c-8.341,8.341-8.341,21.845,0,30.165c4.16,4.181,9.621,6.251,15.083,6.251s10.923-2.069,15.083-6.251l63.979-63.979c1.983-1.963,3.541-4.331,4.629-6.955C512.525,237.606,512.525,231.718,510.371,226.513z M362.68,298.667c-11.797,0-21.333,9.558-21.333,21.333v106.667h-85.333V85.333c0-9.408-6.187-17.728-15.211-20.437l-74.091-22.229h174.635v106.667c0,11.776,9.536,21.333,21.333,21.333s21.333-9.557,21.333-21.333v-128C384.013,9.557,374.477,0,362.68,0H21.347c-0.768,0-1.451,0.32-2.197,0.405c-1.003,0.107-1.92,0.277-2.88,0.512c-2.24,0.576-4.267,1.451-6.165,2.645C9.636,3.861,9.06,3.882,8.612,4.223C8.44,4.352,8.376,4.587,8.205,4.715C5.88,6.549,3.939,8.789,2.531,11.456c-0.299,0.576-0.363,1.195-0.597,1.792c-0.683,1.621-1.429,3.2-1.685,4.992c-0.107,0.64,0.085,1.237,0.064,1.856c-0.021,0.427-0.299,0.811-0.299,1.237V448c0,10.176,7.189,18.923,17.152,20.907l213.333,42.667c1.387,0.299,2.795,0.427,4.181,0.427c4.885,0,9.685-1.685,13.525-4.843c4.928-4.054,7.808-10.091,7.808-16.491v-21.333H362.68c11.797,0,21.333-9.557,21.333-21.333V320C384.013,308.225,374.477,298.667,362.68,298.667z",
        view: "0 0 512 512"
    },
    star : {
        path: "M503.713,224.356L399.809,325.652l24.523,143.039c1.758,10.299-2.499,20.721-10.943,26.872c-4.778,3.475-10.464,5.245-16.148,5.245c-4.366,0-8.761-1.043-12.783-3.158L256,430.12l-128.457,67.53c-9.255,4.875-20.46,4.051-28.932-2.06c-8.445-6.151-12.688-16.56-10.916-26.872l24.538-143.039L8.301,224.37c-7.511-7.292-10.175-18.208-6.976-28.149c3.24-9.941,11.823-17.178,22.176-18.688l143.616-20.885L231.38,26.501c4.6-9.392,14.157-15.324,24.606-15.324c10.477,0,19.992,5.932,24.62,15.311l64.263,130.146l143.644,20.885c10.326,1.497,18.936,8.747,22.121,18.688C513.901,206.148,511.169,217.065,503.713,224.356z",
        view: "0 0 512 512"
    },
    heart : {
        path: "M255,489.6l-35.7-35.7C86.7,336.6,0,257.55,0,160.65C0,81.6,61.2,20.4,140.25,20.4c43.35,0,86.7,20.4,114.75,53.55C283.05,40.8,326.4,20.4,369.75,20.4C448.8,20.4,510,81.6,510,160.65c0,96.9-86.7,175.95-219.3,293.25L255,489.6z",
        view: "0 0 512 512"
    },
    heartCk: {
        path: "M255,489.6L219.3,453.9C86.7,336.6,0,257.55,0,160.65C0,81.6,61.2,20.4,140.25,20.4c43.35,0,86.7,20.4,114.75,53.55C283.05,40.8,326.4,20.4,369.75,20.4C448.8,20.4,510,81.6,510,160.65c0,96.9-86.7,175.95-219.3,293.25L255,489.6z",
        view: "0 0 512 512"
    },
    school: {
        path: "m497 261.15h-106.157v-78.198c0-5.454-2.96-10.478-7.73-13.121l-112.113-62.119v-24.856h49.073c8.284 0 15-6.716 15-15v-48.692c0-8.284-6.716-15-15-15h-64.073c-8.284 0-15 6.716-15 15v48.692 39.855l-112.112 62.12c-4.771 2.643-7.73 7.667-7.73 13.121v78.198h-106.158c-8.284 0-15 6.716-15 15v216.686c0 8.284 6.716 15 15 15h482c8.284 0 15-6.716 15-15v-216.686c0-8.284-6.716-15-15-15zm-191.927-208.294h-34.073v-18.692h34.073zm-275.073 238.294h91.157v186.686h-91.157zm121.157-15v-84.36l104.843-58.093 104.843 58.093v84.36 201.686h-28.964v-82.371c0-41.549-33.802-75.351-75.351-75.351h-1.057c-41.549 0-75.351 33.802-75.351 75.351v82.371h-28.964v-201.686zm58.964 119.315c0-19.947 12.949-36.914 30.879-42.969v125.341h-30.879zm60.879 82.371v-125.341c17.929 6.055 30.879 23.023 30.879 42.969v82.371h-30.879zm211 0h-91.157v-186.686h91.157z",
        view: "0 0 512 512"
    },
    store: {
        path: "M347.7,263.75h-66.5c-18.2,0-33,14.8-33,33v51c0,18.2,14.8,33,33,33h66.5c18.2,0,33-14.8,33-33v-51C380.7,278.55,365.9,263.75,347.7,263.75z M356.7,347.75c0,5-4.1,9-9,9h-66.5c-5,0-9-4.1-9-9v-51c0-5,4.1-9,9-9h66.5c5,0,9,4.1,9,9V347.75z M489.4,171.05c0-2.1-0.5-4.1-1.6-5.9l-72.8-128c-2.1-3.7-6.1-6.1-10.4-6.1H84.7c-4.3,0-8.3,2.3-10.4,6.1l-72.7,128c-1,1.8-1.6,3.8-1.6,5.9c0,28.7,17.3,53.3,42,64.2v211.1c0,6.6,5.4,12,12,12h66.3c0.1,0,0.2,0,0.3,0h93c0.1,0,0.2,0,0.3,0h221.4c6.6,0,12-5.4,12-12v-209.6c0-0.5,0-0.9-0.1-1.3C472,224.55,489.4,199.85,489.4,171.05z M91.7,55.15h305.9l56.9,100.1H34.9L91.7,55.15z M348.3,179.15c-3.8,21.6-22.7,38-45.4,38c-22.7,0-41.6-16.4-45.4-38H348.3z M232,179.15c-3.8,21.6-22.7,38-45.4,38s-41.6-16.4-45.5-38H232z M24.8,179.15h90.9c-3.8,21.6-22.8,38-45.5,38C47.5,217.25,28.6,200.75,24.8,179.15z M201.6,434.35h-69v-129.5c0-9.4,7.6-17.1,17.1-17.1h34.9c9.4,0,17.1,7.6,17.1,17.1v129.5H201.6z M423.3,434.35H225.6v-129.5c0-22.6-18.4-41.1-41.1-41.1h-34.9c-22.6,0-41.1,18.4-41.1,41.1v129.6H66v-193.3c1.4,0.1,2.8,0.1,4.2,0.1c24.2,0,45.6-12.3,58.2-31c12.6,18.7,34,31,58.2,31s45.5-12.3,58.2-31c12.6,18.7,34,31,58.1,31c24.2,0,45.5-12.3,58.1-31c12.6,18.7,34,31,58.2,31c1.4,0,2.7-0.1,4.1-0.1L423.3,434.35L423.3,434.35z M419.2,217.25c-22.7,0-41.6-16.4-45.4-38h90.9C460.8,200.75,441.9,217.25,419.2,217.25z",
        view: "0 0 512 512"
    },
    setting: {
        path: "M454.2,189.101l-33.6-5.7c-3.5-11.3-8-22.2-13.5-32.6l19.8-27.7c8.4-11.8,7.1-27.9-3.2-38.1l-29.8-29.8c-5.6-5.6-13-8.7-20.9-8.7c-6.2,0-12.1,1.9-17.1,5.5l-27.8,19.8c-10.8-5.7-22.1-10.4-33.8-13.9l-5.6-33.2c-2.4-14.3-14.7-24.7-29.2-24.7h-42.1c-14.5,0-26.8,10.4-29.2,24.7l-5.8,34c-11.2,3.5-22.1,8.1-32.5,13.7l-27.5-19.8c-5-3.6-11-5.5-17.2-5.5c-7.9,0-15.4,3.1-20.9,8.7l-29.9,29.8c-10.2,10.2-11.6,26.3-3.2,38.1l20,28.1c-5.5,10.5-9.9,21.4-13.3,32.7l-33.2,5.6c-14.3,2.4-24.7,14.7-24.7,29.2v42.1c0,14.5,10.4,26.8,24.7,29.2l34,5.8c3.5,11.2,8.1,22.1,13.7,32.5l-19.7,27.4c-8.4,11.8-7.1,27.9,3.2,38.1l29.8,29.8c5.6,5.6,13,8.7,20.9,8.7c6.2,0,12.1-1.9,17.1-5.5l28.1-20c10.1,5.3,20.7,9.6,31.6,13l5.6,33.6c2.4,14.3,14.7,24.7,29.2,24.7h42.2c14.5,0,26.8-10.4,29.2-24.7l5.7-33.6c11.3-3.5,22.2-8,32.6-13.5l27.7,19.8c5,3.6,11,5.5,17.2,5.5l0,0c7.9,0,15.3-3.1,20.9-8.7l29.8-29.8c10.2-10.2,11.6-26.3,3.2-38.1l-19.8-27.8c5.5-10.5,10.1-21.4,13.5-32.6l33.6-5.6c14.3-2.4,24.7-14.7,24.7-29.2v-42.1C478.9,203.801,468.5,191.501,454.2,189.101z M451.9,260.401c0,1.3-0.9,2.4-2.2,2.6l-42,7c-5.3,0.9-9.5,4.8-10.8,9.9c-3.8,14.7-9.6,28.8-17.4,41.9c-2.7,4.6-2.5,10.3,0.6,14.7l24.7,34.8c0.7,1,0.6,2.5-0.3,3.4l-29.8,29.8c-0.7,0.7-1.4,0.8-1.9,0.8c-0.6,0-1.1-0.2-1.5-0.5l-34.7-24.7c-4.3-3.1-10.1-3.3-14.7-0.6c-13.1,7.8-27.2,13.6-41.9,17.4c-5.2,1.3-9.1,5.6-9.9,10.8l-7.1,42c-0.2,1.3-1.3,2.2-2.6,2.2h-42.1c-1.3,0-2.4-0.9-2.6-2.2l-7-42c-0.9-5.3-4.8-9.5-9.9-10.8c-14.3-3.7-28.1-9.4-41-16.8c-2.1-1.2-4.5-1.8-6.8-1.8c-2.7,0-5.5,0.8-7.8,2.5l-35,24.9c-0.5,0.3-1,0.5-1.5,0.5c-0.4,0-1.2-0.1-1.9-0.8l-29.8-29.8c-0.9-0.9-1-2.3-0.3-3.4l24.6-34.5c3.1-4.4,3.3-10.2,0.6-14.8c-7.8-13-13.8-27.1-17.6-41.8c-1.4-5.1-5.6-9-10.8-9.9l-42.3-7.2c-1.3-0.2-2.2-1.3-2.2-2.6v-42.1c0-1.3,0.9-2.4,2.2-2.6l41.7-7c5.3-0.9,9.6-4.8,10.9-10c3.7-14.7,9.4-28.9,17.1-42c2.7-4.6,2.4-10.3-0.7-14.6l-24.9-35c-0.7-1-0.6-2.5,0.3-3.4l29.8-29.8c0.7-0.7,1.4-0.8,1.9-0.8c0.6,0,1.1,0.2,1.5,0.5l34.5,24.6c4.4,3.1,10.2,3.3,14.8,0.6c13-7.8,27.1-13.8,41.8-17.6c5.1-1.4,9-5.6,9.9-10.8l7.2-42.3c0.2-1.3,1.3-2.2,2.6-2.2h42.1c1.3,0,2.4,0.9,2.6,2.2l7,41.7c0.9,5.3,4.8,9.6,10,10.9c15.1,3.8,29.5,9.7,42.9,17.6c4.6,2.7,10.3,2.5,14.7-0.6l34.5-24.8c0.5-0.3,1-0.5,1.5-0.5c0.4,0,1.2,0.1,1.9,0.8l29.8,29.8c0.9,0.9,1,2.3,0.3,3.4l-24.7,34.7c-3.1,4.3-3.3,10.1-0.6,14.7c7.8,13.1,13.6,27.2,17.4,41.9c1.3,5.2,5.6,9.1,10.8,9.9l42,7.1c1.3,0.2,2.2,1.3,2.2,2.6v42.1H451.9z M239.4,136.001c-57,0-103.3,46.3-103.3,103.3s46.3,103.3,103.3,103.3s103.3-46.3,103.3-103.3S296.4,136.001,239.4,136.001z M239.4,315.601c-42.1,0-76.3-34.2-76.3-76.3s34.2-76.3,76.3-76.3s76.3,34.2,76.3,76.3S281.5,315.601,239.4,315.601z",
        view: "0 0 512 512"
    },
    comment: {
        path: "M477.371,127.44c-22.843-28.074-53.871-50.249-93.076-66.523c-39.204-16.272-82.035-24.41-128.478-24.41c-34.643,0-67.762,4.805-99.357,14.417c-31.595,9.611-58.812,22.602-81.653,38.97c-22.845,16.37-41.018,35.832-54.534,58.385C6.757,170.833,0,194.484,0,219.228c0,28.549,8.61,55.3,25.837,80.234c17.227,24.931,40.778,45.871,70.664,62.811c-2.096,7.611-4.57,14.846-7.426,21.693c-2.855,6.852-5.424,12.474-7.708,16.851c-2.286,4.377-5.376,9.233-9.281,14.562c-3.899,5.328-6.849,9.089-8.848,11.275c-1.997,2.19-5.28,5.812-9.851,10.849c-4.565,5.048-7.517,8.329-8.848,9.855c-0.193,0.089-0.953,0.952-2.285,2.567c-1.331,1.615-1.999,2.423-1.999,2.423l-1.713,2.566c-0.953,1.431-1.381,2.334-1.287,2.707c0.096,0.373-0.094,1.331-0.57,2.851c-0.477,1.526-0.428,2.669,0.142,3.433v0.284c0.765,3.429,2.43,6.187,4.998,8.277c2.568,2.092,5.474,2.95,8.708,2.563c12.375-1.522,23.223-3.606,32.548-6.276c49.87-12.758,93.649-35.782,131.334-69.097c14.272,1.522,28.072,2.286,41.396,2.286c46.442,0,89.271-8.138,128.479-24.417c39.208-16.272,70.233-38.448,93.072-66.517c22.843-28.062,34.263-58.663,34.263-91.781C511.626,186.108,500.207,155.509,477.371,127.44z",
        view: "0 0 512 512"
    },
    user: {
        path: "M149.996,0C67.157,0,0.001,67.158,0.001,149.997c0,82.837,67.156,150,149.995,150s150-67.163,150-150C299.996,67.156,232.835,0,149.996,0z M150.453,220.763v-0.002h-0.916H85.465c0-46.856,41.152-46.845,50.284-59.097l1.045-5.587c-12.83-6.502-21.887-22.178-21.887-40.512c0-24.154,15.712-43.738,35.089-43.738c19.377,0,35.089,19.584,35.089,43.738c0,18.178-8.896,33.756-21.555,40.361l1.19,6.349c10.019,11.658,49.802,12.418,49.802,58.488H150.453z",
        view: "0 0 299.997 299.997"
    }
}

class SVG extends React.Component {
    render() {
        return (
            <svg
                x="0px" y="0px"
                width={this.props.width ? this.props.width : "auto"}
                height={this.props.height ? this.props.height : "auto"}
                viewBox={svg[this.props.name].view}>
                <path
                    fill={this.props.color}
                    d={svg[this.props.name].path}
                />
            </svg>
        );
    }
}

export default SVG;