package com.nimain.core.domain.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class AppScope(val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default))