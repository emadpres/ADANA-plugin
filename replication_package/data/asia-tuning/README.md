## ground-truth.xlsx
	The oracle we built for tuning ASIA. The Oracle includes 10 snippets and 36 candidate clones for each of them. In total, it includes 95 true positives and 265 false positives.

## ground-truth-codes/:
	Inside each of 10 subdirectories, Three are 1+36 code snippet corresponding to the main snippet and its 36 candidate clones.

## tuning-of-asia-parameters.xlsx
	Each row reports one configurations and the resulting "%_coveredSnippets" and "precisiona"
	- The "approach" column: Indicates the α, β values (e.g., 0.7-0.3 means α=0.7, β=0.3)
	- The "threshold" column: Indicaites the t value
