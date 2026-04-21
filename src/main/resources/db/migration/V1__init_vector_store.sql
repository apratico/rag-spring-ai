-- pgvector schema aligned with Spring AI PgVectorStore defaults.
-- Avoids runtime initialize-schema to keep the bootstrap reproducible.

CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Main store (OpenAI text-embedding-3-small: 1536 dims)
CREATE TABLE IF NOT EXISTS public.vector_store (
    id        UUID        PRIMARY KEY DEFAULT uuid_generate_v4(),
    content   TEXT,
    metadata  JSON,
    embedding VECTOR(1536)
);

CREATE INDEX IF NOT EXISTS vector_store_embedding_idx
    ON public.vector_store
    USING HNSW (embedding vector_cosine_ops);

-- Alternate store used by the `ollama` profile (nomic-embed-text: 768 dims)
CREATE TABLE IF NOT EXISTS public.vector_store_ollama (
    id        UUID        PRIMARY KEY DEFAULT uuid_generate_v4(),
    content   TEXT,
    metadata  JSON,
    embedding VECTOR(768)
);

CREATE INDEX IF NOT EXISTS vector_store_ollama_embedding_idx
    ON public.vector_store_ollama
    USING HNSW (embedding vector_cosine_ops);
