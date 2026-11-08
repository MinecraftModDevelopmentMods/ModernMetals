package zone.moddev.mc.modernmetals.compat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

class LegacyNamespaceTransformerTests {

	@Test
	void linksOldTypesFieldsInheritanceAndReflectionToCurrentClasses() {
		final byte[] output = new LegacyNamespaceTransformer().transform("example.Consumer", null, fixture());
		final ClassNode type = new ClassNode();
		new ClassReader(output).accept(type, 0);
		assertEquals("example/Consumer", type.name);
		assertEquals("zone/moddev/mc/modernmetals/proxy/CommonProxy", type.superName);
		assertEquals("Lzone/moddev/mc/modernmetals/ModernMetals;", ((FieldNode) type.fields.get(0)).desc);

		final MethodNode method = (MethodNode) type.methods.get(0);
		assertEquals("zone/moddev/mc/modernmetals/ModernMetals", ((FieldInsnNode) method.instructions.get(0)).owner);
		assertEquals("zone.moddev.mc.modernmetals.init.Materials", ((LdcInsnNode) method.instructions.get(2)).cst);
		assertEquals(Type.getObjectType("zone/moddev/mc/modernmetals/util/Config$Options"),
				((LdcInsnNode) method.instructions.get(4)).cst);
		assertEquals("other.mod/textures/ore.png", ((LdcInsnNode) method.instructions.get(6)).cst);
	}

	@Test
	void doesNotRelocateAddonOwnedClassesInsideTheHistoricalPackage() {
		final ClassWriter source = new ClassWriter(0);
		source.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "com/mcmoddev/modernmetals/addon/Extra", null,
				"com/mcmoddev/modernmetals/proxy/CommonProxy", null);
		source.visitEnd();

		final ClassNode type = new ClassNode();
		new ClassReader(new LegacyNamespaceTransformer().transform("com.mcmoddev.modernmetals.addon.Extra", null,
				source.toByteArray())).accept(type, 0);
		assertEquals("com/mcmoddev/modernmetals/addon/Extra", type.name);
		assertEquals("zone/moddev/mc/modernmetals/proxy/CommonProxy", type.superName);
	}

	@Test
	void leavesCanonicalClassesAndTheCyanoBridgeAlone() {
		final LegacyNamespaceTransformer transformer = new LegacyNamespaceTransformer();
		final byte[] bytes = fixture();
		assertSame(bytes, transformer.transform("zone.moddev.mc.modernmetals.ModernMetals", null, bytes));
		assertNull(transformer.transform("missing", "missing", null));

		final ClassWriter source = new ClassWriter(0);
		source.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "example/Golem", null, "java/lang/Object", null);
		source.visitField(Opcodes.ACC_PUBLIC, "old", "Lcyano/modernmetals/init/Blocks;", null, null).visitEnd();
		source.visitEnd();
		final byte[] golem = source.toByteArray();
		assertSame(golem, transformer.transform("example.Golem", null, golem));
	}

	private static byte[] fixture() {
		final ClassWriter type = new ClassWriter(0);
		type.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "example/Consumer", null,
				"com/mcmoddev/modernmetals/proxy/CommonProxy", null);
		type.visitField(Opcodes.ACC_PUBLIC, "mod", "Lcom/mcmoddev/modernmetals/ModernMetals;", null, null).visitEnd();

		final MethodVisitor method = type.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "link", "()V", null, null);
		method.visitCode();
		method.visitFieldInsn(Opcodes.GETSTATIC, "com/mcmoddev/modernmetals/ModernMetals", "instance", "Lcom/mcmoddev/modernmetals/ModernMetals;");
		method.visitInsn(Opcodes.POP);
		method.visitLdcInsn("com.mcmoddev.modernmetals.init.Materials");
		method.visitInsn(Opcodes.POP);
		method.visitLdcInsn(Type.getObjectType("com/mcmoddev/modernmetals/util/Config$Options"));
		method.visitInsn(Opcodes.POP);
		method.visitLdcInsn("other.mod/textures/ore.png");
		method.visitInsn(Opcodes.POP);
		method.visitInsn(Opcodes.RETURN);
		method.visitMaxs(1, 0);
		method.visitEnd();
		type.visitEnd();
		return type.toByteArray();
	}
}
