# أبني لغتي – تطبيق أندرويد
تطوير: Dahshan Soft

- ملف التطبيق: `app/src/main/assets/index.html`
- عند كل رفع (push) يبني GitHub Actions ملف `abni-lughati.apk` تلقائيًا.
- لتحميله: Actions ← آخر عملية ناجحة ← Artifacts ← abni-lughati-apk
- عند تحديث التطبيق: استبدل index.html وارفع رقم `versionCode` في `app/build.gradle`.
