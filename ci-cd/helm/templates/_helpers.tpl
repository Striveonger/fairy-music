{{/*
Expand the name of the chart.
*/}}
{{- define "fairy-music.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "fairy-music.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "fairy-music.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "fairy-music.labels" -}}
helm.sh/chart: {{ include "fairy-music.chart" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "fairy-music-api.selectorLabels" -}}
app.kubernetes.io/name: {{ include "fairy-music.name" . }}-api
app.kubernetes.io/instance: {{ .Release.Name }}-api
app.kubernetes.io/namespace: {{ .Release.Namespace }}
{{- end }}

{{- define "fairy-music-ui.selectorLabels" -}}
app.kubernetes.io/name: {{ include "fairy-music.name" . }}-ui
app.kubernetes.io/instance: {{ .Release.Name }}-ui
app.kubernetes.io/namespace: {{ .Release.Namespace }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "fairy-music.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "fairy-music.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}