package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppNotificationEntity
import com.example.data.UserProfileEntity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountAndAuthDialog(
    user: UserProfileEntity,
    onDismiss: () -> Unit,
    onSaveProfile: (String, String, Int, String, String) -> Unit,
    onPasswordReset: (String) -> Unit
) {
    var mode by remember { mutableStateOf("PERFIL") } // PERFIL, LOGIN, CADASTRO
    var name by remember(user) { mutableStateOf(user.name) }
    var email by remember(user) { mutableStateOf(user.email) }
    var password by remember { mutableStateOf("••••••••") }
    var targetScore by remember(user) { mutableIntStateOf(user.targetScore) }
    var selectedRole by remember(user) { mutableStateOf(user.role) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Sistema de Cadastro, Login e Perfil",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("PERFIL" to "Meu Perfil", "LOGIN" to "Login", "CADASTRO" to "Criar Conta").forEach { (k, label) ->
                        FilterChip(
                            selected = mode == k,
                            onClick = { mode = k },
                            label = { Text(label) }
                        )
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome Completo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_name_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail Acadêmico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_email_input"),
                    singleLine = true
                )

                if (mode != "PERFIL") {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Senha") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                Text(
                    text = "Objetivo de Nota no ENEM: $targetScore pontos",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(800, 900, 960, 1000).forEach { goal ->
                        FilterChip(
                            selected = targetScore == goal,
                            onClick = { targetScore = goal },
                            label = { Text("$goal+") }
                        )
                    }
                }

                Text(
                    text = "Tipo de Acesso:",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("ALUNO", "PROFESSOR", "ADMIN").forEach { r ->
                        FilterChip(
                            selected = selectedRole == r,
                            onClick = { selectedRole = r },
                            label = { Text(r) }
                        )
                    }
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = {
                            onSaveProfile(name, email, targetScore, selectedRole, "Google OAuth")
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("google_login_button")
                    ) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Login com Google")
                    }
                    OutlinedButton(
                        onClick = { onPasswordReset(email) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.LockReset, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Recuperar Senha")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSaveProfile(name, email, targetScore, selectedRole, "E-mail & Senha")
                    onDismiss()
                },
                modifier = Modifier.testTag("save_profile_button")
            ) {
                Text("Salvar e Entrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun NotificationsDialog(
    notifications: List<AppNotificationEntity>,
    onMarkAllRead: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(
                text = "Central de Notificações e Metas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.height(320.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notifications, key = { it.id }) { item ->
                    Surface(
                        color = if (item.isRead) {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        } else {
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.type,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                if (!item.isRead) {
                                    Text(
                                        text = "NOVA",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.message,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onMarkAllRead()
                    onDismiss()
                }
            ) {
                Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Marcar Todas como Lidas")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}
